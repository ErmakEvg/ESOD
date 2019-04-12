package by.agat.system.controller;

import by.agat.system.domain.*;
import by.agat.system.services.DocumentService;
import by.agat.system.services.DocumentStatusService;
import by.agat.system.services.UserService;
import by.agat.system.utility.FileWorker;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.print.Doc;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/inspector")
@PropertySource(value = {"classpath:application.properties"}, encoding = "windows-1251")
public class InspectorController {

    @Value("${pathFileUploadServer}")
    private String pathToUpload;

    @Autowired
    private UserService userService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentStatusService documentStatusService;

    @GetMapping({"/", ""})
    public ModelAndView inspectorPage(Authentication authentication, ModelAndView model) {
        UserDetails user = (UserDetails)authentication.getPrincipal();
        model.addObject("user", user);
        model.setViewName("inspector/index");
        return model;
    }

    @GetMapping("/uploadfiles")
    public ModelAndView uploadFilePage(Authentication authentication, ModelAndView model) {
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        model.addObject("user", user);

        model.addObject("document", new UploadDocument());
        model.setViewName("inspector/uploadfile");
        return model;
    }


    @GetMapping("/getUploadFiles")
    public @ResponseBody List<Document> getUploadFiles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        //List<Document> documents = user.getDocuments().stream().filter(document -> sd.format(document.getDateUpload()).equals(sd.format(new Date()))).collect(Collectors.toList());
        List<Document> documents = documentService.getDocumentsByUserAndCurrDate(user.getUser_id());
        return documents;
    }

    @PostMapping("/upload")
    public @ResponseBody
    String fileUpload(@ModelAttribute UploadDocument document, Authentication authentication) {
        String newPAth = pathToUpload;
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        try {
            for (MultipartFile file: document.getFile() ) {
                String nameFile = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('/') + 1);
                Document doc = new Document();
                doc.setName(nameFile);
                doc.setPath(newPAth + nameFile);
                TypeDocument type = new TypeDocument();
                type.setType_id(1);
                doc.setType(type);
                doc.setUser(user);
                String codeBankStr = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('/') + 1, file.getOriginalFilename().lastIndexOf('/'));
                int codeBank = Integer.parseInt(codeBankStr);
                doc.setBank(codeBank);
                doc.setDateUpload(new Date());
                byte[] bytes = file.getBytes();
                Path path = Paths.get(newPAth + nameFile);
                Files.write(path, bytes);
                documentService.save(doc);
                DocumentStatus docStatus = new DocumentStatus();
                Status status = new Status();
                status.setId(1);
                docStatus.setStatus(status);
                docStatus.setDateChange(new Date());
                docStatus.setDocument(doc);
                docStatus.setUser(user);
                documentStatusService.save(docStatus);

            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Что то пошло не так!!!!!!!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Что то пошло не так!!!!!!!";
        }
        return "Файлы успешно загружены на сервер!";
    }

}
