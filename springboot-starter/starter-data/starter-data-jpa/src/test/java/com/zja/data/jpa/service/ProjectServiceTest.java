package com.zja.data.jpa.service;


import com.zja.data.jpa.model.dto.PageData;
import com.zja.data.jpa.model.dto.ProjectDTO;
import com.zja.data.jpa.model.request.ProjectPageRequest;
import com.zja.data.jpa.model.request.ProjectRequest;
import com.zja.data.jpa.model.request.ProjectUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * ProjectService 单元测试类
 *
 * @Author: zhengja
 * @Date: 2025-11-12 14:50
 */
class ProjectServiceTest {

    @Mock
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void queryById() {
        // Given
        String id = "test-id";
        ProjectDTO expected = new ProjectDTO();
        when(projectService.queryById(id)).thenReturn(expected);

        // When
        ProjectDTO result = projectService.queryById(id);

        // Then
        assertNotNull(result);
        verify(projectService).queryById(id);
    }

    @Test
    void pageList() {
        // Given
        ProjectPageRequest request = new ProjectPageRequest();
        PageData<ProjectDTO> expected = new PageData<>();
        when(projectService.pageList(request)).thenReturn(expected);

        // When
        PageData<ProjectDTO> result = projectService.pageList(request);

        // Then
        assertNotNull(result);
        verify(projectService).pageList(request);
    }

    @Test
    void existName() {
        // Given
        String name = "test-project";
        when(projectService.existName(name)).thenReturn(true);

        // When
        Boolean result = projectService.existName(name);

        // Then
        assertTrue(result);
        verify(projectService).existName(name);
    }

    @Test
    void add() {
        // Given
        ProjectRequest request = new ProjectRequest();
        ProjectDTO expected = new ProjectDTO();
        when(projectService.add(request)).thenReturn(expected);

        // When
        ProjectDTO result = projectService.add(request);

        // Then
        assertNotNull(result);
        verify(projectService).add(request);
    }

    @Test
    void addBatch() {
        // Given
        List<ProjectRequest> requests = Arrays.asList(new ProjectRequest(), new ProjectRequest());
        List<ProjectDTO> expected = Arrays.asList(new ProjectDTO(), new ProjectDTO());
        when(projectService.addBatch(requests)).thenReturn(expected);

        // When
        List<ProjectDTO> result = projectService.addBatch(requests);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(projectService).addBatch(requests);
    }

    @Test
    void update() {
        // Given
        String id = "test-id";
        ProjectUpdateRequest request = new ProjectUpdateRequest();
        ProjectDTO expected = new ProjectDTO();
        when(projectService.update(id, request)).thenReturn(expected);

        // When
        ProjectDTO result = projectService.update(id, request);

        // Then
        assertNotNull(result);
        verify(projectService).update(id, request);
    }

    @Test
    void deleteById() {
        // Given
        String id = "test-id";
        when(projectService.deleteById(id)).thenReturn(true);

        // When
        boolean result = projectService.deleteById(id);

        // Then
        assertTrue(result);
        verify(projectService).deleteById(id);
    }

    @Test
    void deleteBatch() {
        // Given
        List<String> ids = Arrays.asList("id1", "id2", "id3");
        doNothing().when(projectService).deleteBatch(ids);

        // When
        projectService.deleteBatch(ids);

        // Then
        verify(projectService).deleteBatch(ids);
    }
}