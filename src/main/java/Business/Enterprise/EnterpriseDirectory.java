package Business.Enterprise;

import java.util.ArrayList;

public class EnterpriseDirectory {
    private ArrayList<Enterprise> enterpriseList;
    public EnterpriseDirectory() { enterpriseList = new ArrayList<>(); }
    public ArrayList<Enterprise> getEnterpriseList() { return enterpriseList; }
    public void setEnterpriseList(ArrayList<Enterprise> enterpriseList) { this.enterpriseList = enterpriseList; }

    public Enterprise createAndAddEnterprise(String name, Enterprise.EnterpriseType type) {
        Enterprise enterprise = null;
        if (type == Enterprise.EnterpriseType.Hospital) enterprise = new HospitalEnterprise(name);
        else if (type == Enterprise.EnterpriseType.Wholesaler) enterprise = new WholesalerEnterprise(name);
        else if (type == Enterprise.EnterpriseType.Transport) enterprise = new TransportEnterprise(name);
        if (enterprise != null) enterpriseList.add(enterprise);
        return enterprise;
    }
}
