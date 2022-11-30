package monitoring.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import monitoring.dto.SectionDetailDTO;
import monitoring.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @ResponseBody
    @GetMapping("/api/database")
    public ResponseEntity<List<SectionDetailDTO>> sections (){
        return ResponseEntity.ok(applicationService.details());
    }

    @ResponseBody
    @GetMapping("/api/sleep")
    public ResponseEntity  sleep(){
        applicationService.sleep();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/image")
    public String getImagePage(){
        System.out.println("hi");
        return "hello";
    }
}
