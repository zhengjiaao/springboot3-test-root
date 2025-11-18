package com.zja.data.jpa.service;

import com.zja.data.jpa.model.dto.PageData;
import com.zja.data.jpa.model.dto.ProjectDTO;
import com.zja.data.jpa.model.request.ProjectPageRequest;
import com.zja.data.jpa.model.request.ProjectRequest;
import com.zja.data.jpa.model.request.ProjectUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: zhengja
 * @Date: 2025-11-13 11:19
 */
@SpringBootTest
// @ActiveProfiles("test")
class ProjectServiceIntegrationTest {

    @Autowired
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        // 清空数据
        projectService.deleteBatch(projectService.pageList(new ProjectPageRequest()).getData().stream().map(ProjectDTO::getId).toList());
    }

    @Test
    void testQueryById() {
        // Given
        ProjectRequest request = new ProjectRequest();
        request.setName("test-project-query");
        request.setRemarks("测试项目查询");
        ProjectDTO created = projectService.add(request);

        // When
        ProjectDTO result = projectService.queryById(created.getId());

        // Then
        assertNotNull(result);
        assertEquals(created.getId(), result.getId());
        assertEquals("test-project-query", result.getName());
    }

    @Test
    void testPageList() {
        // Given
        ProjectRequest request1 = new ProjectRequest();
        request1.setName("page-test-1");
        request1.setRemarks("分页测试1");
        projectService.add(request1);

        ProjectRequest request2 = new ProjectRequest();
        request2.setName("page-test-2");
        request2.setRemarks("分页测试2");
        projectService.add(request2);

        ProjectPageRequest pageRequest = new ProjectPageRequest();
        pageRequest.setPage(1);
        pageRequest.setSize(10);

        // When
        PageData<ProjectDTO> result = projectService.pageList(pageRequest);

        // Then
        assertNotNull(result);
        assertTrue(result.getCount() >= 2);
        assertFalse(result.getData().isEmpty());
    }

    @Test
    void testExistName() {
        // Given
        String testName = "unique-test-name-" + System.currentTimeMillis();
        ProjectRequest request = new ProjectRequest();
        request.setName(testName);
        request.setRemarks("唯一名称测试");
        projectService.add(request);

        // When
        Boolean result = projectService.existName(testName);

        // Then
        assertTrue(result);
    }

    @Test
    void testAdd() {
        // Given
        ProjectRequest request = new ProjectRequest();
        request.setName("new-project");
        request.setRemarks("新增项目测试");

        // When
        ProjectDTO result = projectService.add(request);

        // Then
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("new-project", result.getName());
        assertEquals("新增项目测试", result.getRemarks());
        assertNotNull(result.getCreateTime());
    }

    @Test
    void testAddBatch() {
        // Given
        ProjectRequest request1 = new ProjectRequest();
        request1.setName("batch-project-1");
        request1.setRemarks("批量新增测试1");

        ProjectRequest request2 = new ProjectRequest();
        request2.setName("batch-project-2");
        request2.setRemarks("批量新增测试2");

        List<ProjectRequest> requests = Arrays.asList(request1, request2);

        // When
        List<ProjectDTO> result = projectService.addBatch(requests);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("batch-project-1", result.get(0).getName());
        assertEquals("batch-project-2", result.get(1).getName());
    }

    @Test
    void testUpdate() {
        // Given
        ProjectRequest addRequest = new ProjectRequest();
        addRequest.setName("original-name");
        addRequest.setRemarks("原始备注");
        ProjectDTO created = projectService.add(addRequest);

        ProjectUpdateRequest updateRequest = new ProjectUpdateRequest();
        updateRequest.setName("updated-name");
        updateRequest.setRemarks("更新后的备注");

        // When
        ProjectDTO result = projectService.update(created.getId(), updateRequest);

        // Then
        assertNotNull(result);
        assertEquals("updated-name", result.getName());
        assertEquals("更新后的备注", result.getRemarks());
        assertNotNull(result.getLastModifiedDate());
    }

    @Test
    void testDeleteById() {
        // Given
        ProjectRequest request = new ProjectRequest();
        request.setName("delete-test");
        request.setRemarks("删除测试");
        ProjectDTO created = projectService.add(request);

        // When
        boolean result = projectService.deleteById(created.getId());

        // Then
        assertTrue(result);

        // Verify deletion
        assertNull(projectService.queryById(created.getId()));
    }

    @Test
    void testDeleteBatch() {
        // Given
        ProjectRequest request1 = new ProjectRequest();
        request1.setName("batch-delete-1");
        ProjectDTO project1 = projectService.add(request1);

        ProjectRequest request2 = new ProjectRequest();
        request2.setName("batch-delete-2");
        ProjectDTO project2 = projectService.add(request2);

        List<String> ids = Arrays.asList(project1.getId(), project2.getId());

        // When
        projectService.deleteBatch(ids);

        // Then
        assertNull(projectService.queryById(project1.getId()));
        assertNull(projectService.queryById(project2.getId()));
    }
}
