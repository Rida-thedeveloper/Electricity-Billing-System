package electricity.billing.system;

public class Customer {
    private String meterNo;
    private String username;
    private String name;
    private String userType;
    private String address;
    private String city;
    private String state;
    private String email;
    private String phone;

    // Constructor for existing customer with all info
    public Customer(String meterNo, String username, String name, String userType,
                    String address, String city, String state, String email, String phone) {
        this.meterNo = meterNo;
        this.username = username;
        this.name = name;
        this.userType = userType;
        this.address = address;
        this.city = city;
        this.state = state;
        this.email = email;
        this.phone = phone;
    }

    //  Constructor matching database structure used in DAO
    public Customer(String name, String meterNo, String address, String city, String state, String email, String phone) {
        this.meterNo = meterNo;
        this.username = "";  // Optional - can be set later if needed
        this.name = name;
        this.userType = "";  // Optional - can be set later
        this.address = address;
        this.city = city;
        this.state = state;
        this.email = email;
        this.phone = phone;
    }

    // Constructor for simpler cases (e.g., login)
    public Customer(String meterNo, String username, String name, String userType) {
        this(meterNo, username, name, userType, "", "", "", "", "");
    }

    // Getters
    public String getMeter() { return meterNo; }
    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getUserType() { return userType; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setName(String name) { this.name = name; }
    public void setUserType(String userType) { this.userType = userType; }
    public void setAddress(String address) { this.address = address; }
    public void setCity(String city) { this.city = city; }
    public void setState(String state) { this.state = state; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
}
