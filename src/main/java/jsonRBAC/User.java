
package jsonRBAC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "username",
    "name",
    "surname",
    "gender",
    "birthDate",
    "enabled",
    "note",
    "creationDt",
    "updatedDt",
    "loginDt",
    "secured",
    "contactDTO",
    "addressDTO",
    "roles",
    "permissions"
})
@Generated("jsonschema2pojo")
public class User {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("name")
    private String name;
    @JsonProperty("surname")
    private String surname;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("birthDate")
    private Object birthDate;
    @JsonProperty("enabled")
    private Boolean enabled;
    @JsonProperty("note")
    private Object note;
    @JsonProperty("creationDt")
    private String creationDt;
    @JsonProperty("updatedDt")
    private String updatedDt;
    @JsonProperty("loginDt")
    private Object loginDt;
    @JsonProperty("secured")
    private Boolean secured;
    @JsonProperty("contactDTO")
    private ContactDTO contactDTO;
    @JsonProperty("addressDTO")
    private AddressDTO addressDTO;
    @JsonProperty("roles")
    private List<String> roles = new ArrayList<String>();
    @JsonProperty("permissions")
    private List<String> permissions = new ArrayList<String>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public User withId(Integer id) {
        this.id = id;
        return this;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    public User withUsername(String username) {
        this.username = username;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public User withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("surname")
    public String getSurname() {
        return surname;
    }

    @JsonProperty("surname")
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public User withSurname(String surname) {
        this.surname = surname;
        return this;
    }

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    public User withGender(String gender) {
        this.gender = gender;
        return this;
    }

    @JsonProperty("birthDate")
    public Object getBirthDate() {
        return birthDate;
    }

    @JsonProperty("birthDate")
    public void setBirthDate(Object birthDate) {
        this.birthDate = birthDate;
    }

    public User withBirthDate(Object birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    @JsonProperty("enabled")
    public Boolean getEnabled() {
        return enabled;
    }

    @JsonProperty("enabled")
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public User withEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @JsonProperty("note")
    public Object getNote() {
        return note;
    }

    @JsonProperty("note")
    public void setNote(Object note) {
        this.note = note;
    }

    public User withNote(Object note) {
        this.note = note;
        return this;
    }

    @JsonProperty("creationDt")
    public String getCreationDt() {
        return creationDt;
    }

    @JsonProperty("creationDt")
    public void setCreationDt(String creationDt) {
        this.creationDt = creationDt;
    }

    public User withCreationDt(String creationDt) {
        this.creationDt = creationDt;
        return this;
    }

    @JsonProperty("updatedDt")
    public String getUpdatedDt() {
        return updatedDt;
    }

    @JsonProperty("updatedDt")
    public void setUpdatedDt(String updatedDt) {
        this.updatedDt = updatedDt;
    }

    public User withUpdatedDt(String updatedDt) {
        this.updatedDt = updatedDt;
        return this;
    }

    @JsonProperty("loginDt")
    public Object getLoginDt() {
        return loginDt;
    }

    @JsonProperty("loginDt")
    public void setLoginDt(Object loginDt) {
        this.loginDt = loginDt;
    }

    public User withLoginDt(Object loginDt) {
        this.loginDt = loginDt;
        return this;
    }

    @JsonProperty("secured")
    public Boolean getSecured() {
        return secured;
    }

    @JsonProperty("secured")
    public void setSecured(Boolean secured) {
        this.secured = secured;
    }

    public User withSecured(Boolean secured) {
        this.secured = secured;
        return this;
    }

    @JsonProperty("contactDTO")
    public ContactDTO getContactDTO() {
        return contactDTO;
    }

    @JsonProperty("contactDTO")
    public void setContactDTO(ContactDTO contactDTO) {
        this.contactDTO = contactDTO;
    }

    public User withContactDTO(ContactDTO contactDTO) {
        this.contactDTO = contactDTO;
        return this;
    }

    @JsonProperty("addressDTO")
    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    @JsonProperty("addressDTO")
    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }

    public User withAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
        return this;
    }

    @JsonProperty("roles")
    public List<String> getRoles() {
        return roles;
    }

    @JsonProperty("roles")
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public User withRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }

    @JsonProperty("permissions")
    public List<String> getPermissions() {
        return permissions;
    }

    @JsonProperty("permissions")
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public User withPermissions(List<String> permissions) {
        this.permissions = permissions;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public User withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(User.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("username");
        sb.append('=');
        sb.append(((this.username == null)?"<null>":this.username));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("surname");
        sb.append('=');
        sb.append(((this.surname == null)?"<null>":this.surname));
        sb.append(',');
        sb.append("gender");
        sb.append('=');
        sb.append(((this.gender == null)?"<null>":this.gender));
        sb.append(',');
        sb.append("birthDate");
        sb.append('=');
        sb.append(((this.birthDate == null)?"<null>":this.birthDate));
        sb.append(',');
        sb.append("enabled");
        sb.append('=');
        sb.append(((this.enabled == null)?"<null>":this.enabled));
        sb.append(',');
        sb.append("note");
        sb.append('=');
        sb.append(((this.note == null)?"<null>":this.note));
        sb.append(',');
        sb.append("creationDt");
        sb.append('=');
        sb.append(((this.creationDt == null)?"<null>":this.creationDt));
        sb.append(',');
        sb.append("updatedDt");
        sb.append('=');
        sb.append(((this.updatedDt == null)?"<null>":this.updatedDt));
        sb.append(',');
        sb.append("loginDt");
        sb.append('=');
        sb.append(((this.loginDt == null)?"<null>":this.loginDt));
        sb.append(',');
        sb.append("secured");
        sb.append('=');
        sb.append(((this.secured == null)?"<null>":this.secured));
        sb.append(',');
        sb.append("contactDTO");
        sb.append('=');
        sb.append(((this.contactDTO == null)?"<null>":this.contactDTO));
        sb.append(',');
        sb.append("addressDTO");
        sb.append('=');
        sb.append(((this.addressDTO == null)?"<null>":this.addressDTO));
        sb.append(',');
        sb.append("roles");
        sb.append('=');
        sb.append(((this.roles == null)?"<null>":this.roles));
        sb.append(',');
        sb.append("permissions");
        sb.append('=');
        sb.append(((this.permissions == null)?"<null>":this.permissions));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.note == null)? 0 :this.note.hashCode()));
        result = ((result* 31)+((this.gender == null)? 0 :this.gender.hashCode()));
        result = ((result* 31)+((this.addressDTO == null)? 0 :this.addressDTO.hashCode()));
        result = ((result* 31)+((this.roles == null)? 0 :this.roles.hashCode()));
        result = ((result* 31)+((this.contactDTO == null)? 0 :this.contactDTO.hashCode()));
        result = ((result* 31)+((this.birthDate == null)? 0 :this.birthDate.hashCode()));
        result = ((result* 31)+((this.enabled == null)? 0 :this.enabled.hashCode()));
        result = ((result* 31)+((this.creationDt == null)? 0 :this.creationDt.hashCode()));
        result = ((result* 31)+((this.surname == null)? 0 :this.surname.hashCode()));
        result = ((result* 31)+((this.permissions == null)? 0 :this.permissions.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.updatedDt == null)? 0 :this.updatedDt.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.secured == null)? 0 :this.secured.hashCode()));
        result = ((result* 31)+((this.loginDt == null)? 0 :this.loginDt.hashCode()));
        result = ((result* 31)+((this.username == null)? 0 :this.username.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof User) == false) {
            return false;
        }
        User rhs = ((User) other);
        return ((((((((((((((((((this.note == rhs.note)||((this.note!= null)&&this.note.equals(rhs.note)))&&((this.gender == rhs.gender)||((this.gender!= null)&&this.gender.equals(rhs.gender))))&&((this.addressDTO == rhs.addressDTO)||((this.addressDTO!= null)&&this.addressDTO.equals(rhs.addressDTO))))&&((this.roles == rhs.roles)||((this.roles!= null)&&this.roles.equals(rhs.roles))))&&((this.contactDTO == rhs.contactDTO)||((this.contactDTO!= null)&&this.contactDTO.equals(rhs.contactDTO))))&&((this.birthDate == rhs.birthDate)||((this.birthDate!= null)&&this.birthDate.equals(rhs.birthDate))))&&((this.enabled == rhs.enabled)||((this.enabled!= null)&&this.enabled.equals(rhs.enabled))))&&((this.creationDt == rhs.creationDt)||((this.creationDt!= null)&&this.creationDt.equals(rhs.creationDt))))&&((this.surname == rhs.surname)||((this.surname!= null)&&this.surname.equals(rhs.surname))))&&((this.permissions == rhs.permissions)||((this.permissions!= null)&&this.permissions.equals(rhs.permissions))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.updatedDt == rhs.updatedDt)||((this.updatedDt!= null)&&this.updatedDt.equals(rhs.updatedDt))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.secured == rhs.secured)||((this.secured!= null)&&this.secured.equals(rhs.secured))))&&((this.loginDt == rhs.loginDt)||((this.loginDt!= null)&&this.loginDt.equals(rhs.loginDt))))&&((this.username == rhs.username)||((this.username!= null)&&this.username.equals(rhs.username))));
    }

}
