
package jsonRBAC;

import java.util.HashMap;
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
    "email",
    "phone",
    "skype",
    "facebook",
    "linkedin",
    "website",
    "contactNote"
})
@Generated("jsonschema2pojo")
public class ContactDTO {

    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private Object phone;
    @JsonProperty("skype")
    private Object skype;
    @JsonProperty("facebook")
    private Object facebook;
    @JsonProperty("linkedin")
    private Object linkedin;
    @JsonProperty("website")
    private Object website;
    @JsonProperty("contactNote")
    private Object contactNote;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    public ContactDTO withEmail(String email) {
        this.email = email;
        return this;
    }

    @JsonProperty("phone")
    public Object getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public ContactDTO withPhone(Object phone) {
        this.phone = phone;
        return this;
    }

    @JsonProperty("skype")
    public Object getSkype() {
        return skype;
    }

    @JsonProperty("skype")
    public void setSkype(Object skype) {
        this.skype = skype;
    }

    public ContactDTO withSkype(Object skype) {
        this.skype = skype;
        return this;
    }

    @JsonProperty("facebook")
    public Object getFacebook() {
        return facebook;
    }

    @JsonProperty("facebook")
    public void setFacebook(Object facebook) {
        this.facebook = facebook;
    }

    public ContactDTO withFacebook(Object facebook) {
        this.facebook = facebook;
        return this;
    }

    @JsonProperty("linkedin")
    public Object getLinkedin() {
        return linkedin;
    }

    @JsonProperty("linkedin")
    public void setLinkedin(Object linkedin) {
        this.linkedin = linkedin;
    }

    public ContactDTO withLinkedin(Object linkedin) {
        this.linkedin = linkedin;
        return this;
    }

    @JsonProperty("website")
    public Object getWebsite() {
        return website;
    }

    @JsonProperty("website")
    public void setWebsite(Object website) {
        this.website = website;
    }

    public ContactDTO withWebsite(Object website) {
        this.website = website;
        return this;
    }

    @JsonProperty("contactNote")
    public Object getContactNote() {
        return contactNote;
    }

    @JsonProperty("contactNote")
    public void setContactNote(Object contactNote) {
        this.contactNote = contactNote;
    }

    public ContactDTO withContactNote(Object contactNote) {
        this.contactNote = contactNote;
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

    public ContactDTO withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ContactDTO.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("email");
        sb.append('=');
        sb.append(((this.email == null)?"<null>":this.email));
        sb.append(',');
        sb.append("phone");
        sb.append('=');
        sb.append(((this.phone == null)?"<null>":this.phone));
        sb.append(',');
        sb.append("skype");
        sb.append('=');
        sb.append(((this.skype == null)?"<null>":this.skype));
        sb.append(',');
        sb.append("facebook");
        sb.append('=');
        sb.append(((this.facebook == null)?"<null>":this.facebook));
        sb.append(',');
        sb.append("linkedin");
        sb.append('=');
        sb.append(((this.linkedin == null)?"<null>":this.linkedin));
        sb.append(',');
        sb.append("website");
        sb.append('=');
        sb.append(((this.website == null)?"<null>":this.website));
        sb.append(',');
        sb.append("contactNote");
        sb.append('=');
        sb.append(((this.contactNote == null)?"<null>":this.contactNote));
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
        result = ((result* 31)+((this.skype == null)? 0 :this.skype.hashCode()));
        result = ((result* 31)+((this.website == null)? 0 :this.website.hashCode()));
        result = ((result* 31)+((this.phone == null)? 0 :this.phone.hashCode()));
        result = ((result* 31)+((this.facebook == null)? 0 :this.facebook.hashCode()));
        result = ((result* 31)+((this.linkedin == null)? 0 :this.linkedin.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.email == null)? 0 :this.email.hashCode()));
        result = ((result* 31)+((this.contactNote == null)? 0 :this.contactNote.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ContactDTO) == false) {
            return false;
        }
        ContactDTO rhs = ((ContactDTO) other);
        return (((((((((this.skype == rhs.skype)||((this.skype!= null)&&this.skype.equals(rhs.skype)))&&((this.website == rhs.website)||((this.website!= null)&&this.website.equals(rhs.website))))&&((this.phone == rhs.phone)||((this.phone!= null)&&this.phone.equals(rhs.phone))))&&((this.facebook == rhs.facebook)||((this.facebook!= null)&&this.facebook.equals(rhs.facebook))))&&((this.linkedin == rhs.linkedin)||((this.linkedin!= null)&&this.linkedin.equals(rhs.linkedin))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.email == rhs.email)||((this.email!= null)&&this.email.equals(rhs.email))))&&((this.contactNote == rhs.contactNote)||((this.contactNote!= null)&&this.contactNote.equals(rhs.contactNote))));
    }

}
