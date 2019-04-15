package by.agat.system.utility;

import by.agat.system.domain.Document;
import by.agat.system.domain.DocumentDTO;
import by.agat.system.domain.User;
import by.agat.system.domain.UserDTO;

public class Converter {

    public static DocumentDTO convertFromDocumentToDTO(Document document) {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setBank(document.getBank());
        documentDTO.setName(document.getName());
        documentDTO.setPath(document.getPath());
        documentDTO.setTypeDoc(document.getType().getType());
        documentDTO.setUuid(document.getUuid());
        documentDTO.setStatus(document.getStatuses().get(document.getStatuses().size() - 1).getStatus().getStatus());
        return documentDTO;
    }

    public static UserDTO convertFromUserToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setCodeBank(user.getCodeBank());
        userDTO.setDateRegistration(user.getDateRegistration());
        userDTO.setEnabled(user.getEnabled());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setId(user.getUser_id());
        userDTO.setSurname(user.getSurname());
        userDTO.setRole(user.getRoles().get(0));
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }
}
