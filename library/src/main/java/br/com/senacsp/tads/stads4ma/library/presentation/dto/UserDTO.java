package br.com.senacsp.tads.stads4ma.library.presentation.dto;

import br.com.senacsp.tads.stads4ma.library.domainmodel.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
public class UserDTO {



    private @Setter @Getter UUID id;

    @NotBlank(message = "Obrigatorio")
    @Size( max = 100, message = "s")
    private @Setter @Getter String name;

    @NotBlank()
    @Size( max = 30, message = "s")
    @Email(message = "")
    private @Setter @Getter String email;

    @NotBlank()
    @Size( min = 6, max = 12, message = "s")
    private @Setter @Getter String password;


    public static UserDTO fromEntity(User user){
        if(user == null) return null;
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public static User fromDTO(UserDTO user){
        if(user == null) return null;
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
