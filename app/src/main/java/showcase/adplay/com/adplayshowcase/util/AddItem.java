package showcase.adplay.com.adplayshowcase.util;

/**
 * Created by toukirul on 2/7/2017.
 */

public class AddItem {

    private String id;
    private String categoryName;
    private String icon;

    private String adTitle;
    private String adUrl;
    private String adIsActive;

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public String getAdIsActive() {
        return adIsActive;
    }

    public void setAdIsActive(String adIsActive) {
        this.adIsActive = adIsActive;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    private String addUrl;
    private String activeOrNot;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getActiveOrNot() {
        return activeOrNot;
    }

    public void setActiveOrNot(String activeOrNot) {
        this.activeOrNot = activeOrNot;
    }


}
