package application.model.viewmodel.common;


import java.util.ArrayList;

public class LayoutHeaderVM {
    private String companyName;
    private ArrayList<HeaderMenuVM>  headerMenuVMArrayList;

    public ArrayList<HeaderMenuVM> getHeaderMenuVMArrayList() {
        return headerMenuVMArrayList;
    }

    public void setHeaderMenuVMArrayList(ArrayList<HeaderMenuVM> headerMenuVMArrayList) {
        this.headerMenuVMArrayList = headerMenuVMArrayList;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
