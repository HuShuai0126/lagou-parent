package com.lagou.edu.service.impl;

import com.lagou.edu.dao.ResumeDAO;
import com.lagou.edu.pojo.Resume;
import com.lagou.edu.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class ResumeServiceImpl implements ResumeService {
    @Autowired
    private ResumeDAO resumeDAO;

    @Override
    public Resume findDefaultResumeByUserId(Long userId) {
        System.out.println("userId = " + userId);
        Resume resume = new Resume();
        resume.setUserId(userId);
        // 查询默认简历
        resume.setIsDefault(1);
        Example<Resume> example = Example.of(resume);
        return resumeDAO.findOne(example).get();
    }
}
