import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
 
@ManagedBean
@RequestScoped
public class ShiroYetkilendirme {
    
    private String kullaniciadi;
    private String parola;
 
    public String getKullaniciadi() {
        return kullaniciadi;
    }
 
    public void setKullaniciadi(String kullaniciadi) {
        this.kullaniciadi = kullaniciadi;
    }
 
    public String getParola() {
        return parola;
    }
 
    public void setParola(String parola) {
        this.parola = parola;
    }
    
    public String girisiKontrolEt(){
        
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(kullaniciadi, parola);
 
        try{
            currentUser.login(token);
        } catch (UnknownAccountException uae ) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Giriþ baþarýsýz", "kullanýcý adýnýz yanlýþ"));
            return null;
        } catch (IncorrectCredentialsException ice ) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Giriþ baþarýsýz", "parolanýz yanlýþ"));
            return null;
        } catch (LockedAccountException lae ) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Giriþ baþarýsýz", "Bu kullanýcý adý kilitli"));
            return null;
        } catch(AuthenticationException aex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Giriþ baþarýsýz", aex.toString()));
            return null;
        }
        if(kullaniciadi.equals("yonetici"))
        return "admin.xhtml?faces-redirect=true";
        else
        return "bilgilendirme.xhtml?faces-redirect=true";
    }
    
    public String logoutT() {
 
        Subject currentUser = SecurityUtils.getSubject();
        
        try {
            currentUser.logout();
        } catch (Exception ex) {
            
        }
        
        return "giris.xhtml?faces-redirect=true";
    }  
    
}