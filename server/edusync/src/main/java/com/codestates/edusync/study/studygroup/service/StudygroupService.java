package com.codestates.edusync.study.studygroup.service;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import com.codestates.edusync.globalutils.VerifyStudygroupUtils;
import com.codestates.edusync.study.studygroup.entity.Studygroup;
import com.codestates.edusync.study.studygroup.repository.StudygroupRepository;
import com.codestates.edusync.study.studygroup.utils.StudygroupManager;
import com.codestates.edusync.studyaddons.searchtag.service.SearchTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class StudygroupService implements StudygroupManager{
    private final StudygroupRepository studygroupRepository;
    private final SearchTagService searchTagService;
    private final VerifyStudygroupUtils studygroupUtills;

    @Override
    public Studygroup createStudygruop(Studygroup studygroup) {
        return studygroupRepository.save(studygroup);
    }

    @Override
    public Studygroup updateStudygroup(String email, Studygroup studygroup) {
        Studygroup findStudygroup = findStudygroup(studygroup.getId());

        if (findStudygroup.getLeaderMember().getEmail().equals(email)) {
            Optional.ofNullable(studygroup.getStudyName()).ifPresent(findStudygroup::setStudyName);
            Optional.ofNullable(studygroup.getDaysOfWeek()).ifPresent(findStudygroup::setDaysOfWeek);
            Optional.ofNullable(studygroup.getStudyPeriodStart()).ifPresent(findStudygroup::setStudyPeriodStart);
            Optional.ofNullable(studygroup.getStudyPeriodEnd()).ifPresent(findStudygroup::setStudyPeriodEnd);
            Optional.ofNullable(studygroup.getStudyTimeStart()).ifPresent(findStudygroup::setStudyTimeStart);
            Optional.ofNullable(studygroup.getStudyTimeEnd()).ifPresent(findStudygroup::setStudyTimeEnd);
            Optional.ofNullable(studygroup.getIntroduction()).ifPresent(findStudygroup::setIntroduction);
            Optional.ofNullable(studygroup.getMemberCountMin()).ifPresent(findStudygroup::setMemberCountMin);
            Optional.ofNullable(studygroup.getMemberCountMax()).ifPresent(findStudygroup::setMemberCountMax);
            Optional.ofNullable(studygroup.getPlatform()).ifPresent(findStudygroup::setPlatform);
            Optional.ofNullable(studygroup.getSearchTags()).ifPresent(findStudygroup::setSearchTags);
        } else throw new BusinessLogicException(ExceptionCode.INVALID_PERMISSION);

        return studygroupRepository.save(findStudygroup);
    }

    @Override
    public void updateStatusStudygroup(String email, Long studygroupId) {
        Studygroup findStudygroup = findStudygroup(studygroupId);

        if (findStudygroup.getLeaderMember().getEmail().equals(email)) {
            boolean requited = findStudygroup.getIs_requited();
            if (requited) requited = false;
            else requited = true;
            findStudygroup.setIs_requited(requited);
        } else throw new BusinessLogicException(ExceptionCode.INVALID_PERMISSION);

        studygroupRepository.save(findStudygroup);
    }

    @Override
    public Studygroup findStudygroup(Long studygroupId) {
        Studygroup findStudygroup = studygroupUtills.findStudygroup(studygroupId);
        findStudygroup.setSearchTags(searchTagService.getSearchTagList(studygroupId));
        return findStudygroup;
    }

    @Override
    public Page<Studygroup> findStudygroups(Integer page, Integer size) {
        return studygroupRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
    }

    @Override
    public void deleteStudygroup(String email, Long studygroupId){
        if (studygroupUtills.verifyMemberLeaderOfStudygroup(email, studygroupId)) {
            studygroupRepository.deleteById(studygroupId);
        } else throw new BusinessLogicException(ExceptionCode.INVALID_PERMISSION);
    }
}