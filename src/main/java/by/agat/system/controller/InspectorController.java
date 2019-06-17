package by.agat.system.controller;

import by.agat.system.domain.*;
import by.agat.system.services.DocumentService;
import by.agat.system.services.DocumentStatusService;
import by.agat.system.services.UserService;
import by.agat.system.utility.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

    @GetMapping()
    public ModelAndView inspectorPage(Authentication authentication, ModelAndView model) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        model.addObject("user", user);
        model.setViewName("inspector/index");
        return model;
    }

    @GetMapping("/uploadfiles")
    public ModelAndView uploadFilePage(Authentication authentication, ModelAndView model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        model.addObject("user", user);
        model.addObject("document", new UploadDocument());
        model.setViewName("inspector/uploadfile");
        return model;
    }

    @GetMapping("/getUploadFiles")
    public @ResponseBody
    List<DocumentDTO> getUploadFiles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        List<DocumentDTO> documents = new ArrayList<>();
        List<Document> uploadToday = user.getDocuments().stream()
                .filter(document -> (document.getStatuses().get(document.getStatuses().size() - 1).getStatus().getId() == 1
                        || document.getStatuses().get(document.getStatuses().size() - 1).getStatus().getId() == 2) &&
                        sd.format(document.getDateUpload()).equals(sd.format(new Date())))
                .collect(Collectors.toList());
        for (Document document : uploadToday) {
            DocumentDTO documentDTO = Converter.convertFromDocumentToDTO(document);
            documents.add(documentDTO);
        }
        return documents;
    }


    @PostMapping("/uploadSgn")
    public @ResponseBody
    String uploadSgn(@RequestParam("file") MultipartFile file, @RequestParam("path") String pathFile) {
        byte[] bytes = new byte[0];
        try {
            bytes = file.getBytes();
            Path path = Paths.get(pathToUpload + pathFile);
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathToUpload + pathFile), "UTF-8"));
            try {
                out.write(Arrays.toString(bytes));
            } finally {
                out.close();
            }
            Files.write(path, bytes);
            return "SUCCESS";
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @PostMapping("/loadDocument")
    public @ResponseBody
    String fileUpload(@RequestParam("file") MultipartFile[] files) {
        String newPAth = pathToUpload;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        try {
            for (MultipartFile file : files) {
                String originalName = file.getName();
                String delimeter = "\\\\";
                String arrayPath[] = file.getOriginalFilename().split(delimeter);
                String codeBankStr = "";
                Document doc = new Document();
                String nameFile = "";
                nameFile = arrayPath[arrayPath.length - 1];
                codeBankStr = nameFile.substring(1, 4);
                doc.setName(nameFile);
                doc.setPath(newPAth + nameFile);
                TypeDocument type = new TypeDocument();
                type.setType_id(1);
                doc.setType(type);
                doc.setUser(user);
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
            return e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();

        }
        return "OK";
    }

}
