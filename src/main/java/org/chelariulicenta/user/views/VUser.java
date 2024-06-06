package org.chelariulicenta.user.views;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VUser implements Serializable {

    @JsonProperty
    private int idUser;
    @JsonProperty
    private String userName;
    @JsonProperty
    private String userEmail;
    @JsonProperty
    private String userPassword;
    @JsonProperty
    private boolean isAdmin;
}
