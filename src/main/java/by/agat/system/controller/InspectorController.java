package by.agat.system.controller;

import by.agat.system.domain.Document;
import by.agat.system.domain.TypeDocument;
import by.agat.system.domain.UploadDocument;
import by.agat.system.domain.User;
import by.agat.system.services.DocumentService;
import by.agat.system.services.UserService;
import by.agat.system.utility.FileWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Controller
@RequestMapping("/inspector")
public class InspectorController {

    @Value("${pathFileUploadServer}")
    private String pathToUpload;

    @Autowired
    private UserService userService;

    @Autowired
    private DocumentService documentService;

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

    @PostMapping("/upload")
    public @ResponseBody
    String fileUpload(@ModelAttribute UploadDocument document, Authentication authentication) {
        String newPAth = "";
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        try {
            newPAth = new String(pathToUpload.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
               // doc.setDateUpload(new Date());
                byte[] bytes = file.getBytes();
                Path path = Paths.get(newPAth + nameFile);
                Files.write(path, bytes);
                documentService.save(doc);
            }
        } catch (IOException e) {
            return "Что то пошло не так!!!!!!!";
        } catch (Exception e) {
            return "Что то пошло не так!!!!!!!";
        }
        return "Файлы успешно загружены на сервер!";
    }

}
