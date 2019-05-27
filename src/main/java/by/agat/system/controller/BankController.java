package by.agat.system.controller;

import by.agat.system.domain.*;
import by.agat.system.repository.DocumentRepository;
import by.agat.system.services.DocumentService;
import by.agat.system.services.DocumentStatusService;
import by.agat.system.services.UserService;
import by.agat.system.utility.Converter;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private UserService userService;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentStatusService documentStatusService;

    @GetMapping({"/", ""})
    public ModelAndView bankPage(Authentication authentication, ModelAndView model) {
        UserDetails user = (UserDetails)authentication.getPrincipal();
        model.addObject("user", user);
        model.setViewName("bank/index");
        return model;
    }


    @GetMapping("/receive")
    public ModelAndView uploadFilePage(Authentication authentication, ModelAndView model) {
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        model.addObject("user", user);
        model.setViewName("bank/bank");
        return model;
    }

    @GetMapping("/getUploadFiles")
    public @ResponseBody
    List<DocumentDTO> getUploadFilesByBank() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        int codeBank = user.getCodeBank();
        List<DocumentDTO> documents = new ArrayList<>();
        List<Document> uploadToday = documentService.getDocumentsByBankAndStatus(codeBank, 1);
        for (Document document: uploadToday) {
            DocumentDTO documentDTO = Converter.convertFromDocumentToDTO(document);
            documents.add(documentDTO);
        }
        return documents;
    }

    @RequestMapping(value = "/downloadZip", method = RequestMethod.GET)
    public void downloadFile(@RequestParam("files[]") List<Integer> files, HttpServletResponse response) throws IOException  {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        List<String> fileNames = new ArrayList<>();
        for (Integer uuid:files) {
            Long uuidLong = Long.valueOf(uuid);
            Document document = documentService.getDocumentByUUID(uuidLong);
            File file = getFile(document.getPath());
            fileNames.add(document.getPath());
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            DocumentStatus documentStatus = new DocumentStatus();
            documentStatus.setUser(user);
            documentStatus.setDocument(document);
            documentStatus.setDateChange(new Date());
            Status status = new Status();
            status.setId(3);
            documentStatus.setStatus(status);
            documentStatusService.save(documentStatus);
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=\"download.zip\"");

        try (ZipOutputStream zippedOut = new ZipOutputStream(response.getOutputStream())) {
            for (String file : fileNames) {
                FileSystemResource resource = new FileSystemResource(file);
                ZipEntry e = new ZipEntry(resource.getFilename());
                e.setSize(resource.contentLength());
                e.setTime(System.currentTimeMillis());
                zippedOut.putNextEntry(e);
                IOUtils.copy(resource.getInputStream(), zippedOut);
                zippedOut.closeEntry();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File getFile(String filepath) throws FileNotFoundException {
        File file = new File(filepath);
        if (!file.exists()){
            throw new FileNotFoundException("file with path: " + filepath + " was not found.");
        }
        return file;
    }

}
