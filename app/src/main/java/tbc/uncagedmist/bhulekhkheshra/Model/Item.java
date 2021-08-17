package tbc.uncagedmist.bhulekhkheshra.Model;

public class Item {
    private String stateId,stateName,stateImage,stateUrl;

    public Item() {
    }

    public Item(String stateId, String stateName, String stateImage, String stateUrl) {
        this.stateId = stateId;
        this.stateName = stateName;
        this.stateImage = stateImage;
        this.stateUrl = stateUrl;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateImage() {
        return stateImage;
    }

    public void setStateImage(String stateImage) {
        this.stateImage = stateImage;
    }

    public String getStateUrl() {
        return stateUrl;
    }

    public void setStateUrl(String stateUrl) {
        this.stateUrl = stateUrl;
    }
}
