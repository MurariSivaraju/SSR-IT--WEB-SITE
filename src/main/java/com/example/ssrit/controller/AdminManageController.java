package com.example.ssrit.controller;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ssrit.entity.UserRegistration;
import com.example.ssrit.service.AdminService;



@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminManageController {

    private final AdminService adminService;

    public AdminManageController(AdminService adminService) {
        this.adminService = adminService;
    }
    
    /* =============================
       GET USERS (SEARCH + PAGINATION)
       ============================= */
    @GetMapping("/users")
    public Page<UserRegistration> getUsers(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return adminService.getUsers(search, page, size);
    }

    /* =============================
       DELETE USER
       ============================= */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    /* =============================
       DASHBOARD ANALYTICS
       ============================= */
    @GetMapping("/stats")
    public Map<String, Long> getStats() {
        return adminService.getStats();
    }

    /* =============================
       VIEW / DOWNLOAD RESUME
       ============================= */
    @GetMapping("/users/{id}/resume")
    public ResponseEntity<Resource> downloadResume(@PathVariable Long id) {

        File file = adminService.getResumeFile(id);
        if (file == null || !file.exists()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + file.getName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf") // ✅ CRITICAL FIX
                .contentLength(file.length())
                .body(resource);
    }

    
    
 
    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @PostMapping("/auth/login")
    public ResponseEntity<?> adminLogin(@RequestBody Map<String, String> req) {

        String username = req.get("username");
        String password = req.get("password");

        if (adminUsername.equals(username) &&
            adminPassword.equals(password)) {

            return ResponseEntity.ok(Map.of("token", "ADMIN_OK"));
        }

        return ResponseEntity.status(401)
                .body(Map.of("error", "Invalid admin credentials"));
    }

    
    
}
