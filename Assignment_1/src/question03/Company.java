package question03;

public class Company {
    private String companyId;
    private String name;
    private String industryType;
    private String location;

    public Company(String companyId, String name, String industryType, String location) {
        setCompanyId(companyId);
        setName(name);
        setIndustryType(industryType);
        setLocation(location);
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        if (industryType.equalsIgnoreCase("IT") ||
                industryType.equalsIgnoreCase("Finance") ||
                industryType.equalsIgnoreCase("Health") ||
                industryType.equalsIgnoreCase("Education")) {
            this.industryType = industryType;
        } else {
            throw new IllegalArgumentException("Invalid industry type. Must be IT, Finance, Health, or Education.");
        }
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

