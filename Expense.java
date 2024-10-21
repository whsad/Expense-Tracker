import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Expense {

    private String Id;
    private LocalDateTime Date;
    private String Description;
    private double Amount;

    public Expense(String description, double amount) {
        this.Id = UUID.randomUUID().toString();
        this.Description = description;
        this.Amount = amount;
        this.Date = LocalDateTime.now();
    }

    public Expense(String id, String date, String description, String amount) {
        this.Id = id;
        this.Date = LocalDateTime.parse(date);
        this.Description = description;
        this.Amount = Double.parseDouble(amount);
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public LocalDateTime getDate() {
        return Date;
    }

    public void setDate(LocalDateTime date) {
        Date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "Id='" + Id + '\'' +
                ", Date=" + Date +
                ", Description='" + Description + '\'' +
                ", Amount=" + Amount +
                '}';
    }

    public String convertExpenseToJson() {
        return "{"
                + "\"Id\":\"" + Id + "\","
                + "\"Date\":\"" + Date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")) + "\","
                + "\"Description\":\"" + Description + "\","
                + "\"Amount\":" + String.format("%.2f", Amount)
                + "}";
    }

    public static Expense parseExpenseFromJson(String json) {
        String[] jsonArr = json.substring(1, json.length() - 1)
                .replace("\"", "")
                .split(",");

        String id = jsonArr[0].split(":")[1];
        String date = jsonArr[1].split(":", 2)[1];
        String description = jsonArr[2].split(":")[1];
        String amount = jsonArr[3].split(":")[1];

        return new Expense(
                id,
                date,
                description,
                amount
        );
    }
}
