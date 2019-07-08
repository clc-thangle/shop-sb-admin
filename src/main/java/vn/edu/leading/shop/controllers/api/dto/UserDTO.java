package vn.edu.leading.shop.controllers.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.leading.shop.models.BaseModel;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO extends BaseModel<UserDTO> {

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String langKey;

    private String imageUrl;

    private String accessToken;
}
