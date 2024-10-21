import java.io.*;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExpenseManager {
    private final List<Expense> expenses;
    private static final String EXPENSES_PATH = System.getProperty("user.dir") + File.separator + "expenses.json";

    public ExpenseManager() {
        expenses = loadExpenses();
    }

    public void addExpense(String description, double amount) {
        Expense expense = new Expense(description, amount);
        expenses.add(expense);
        System.out.println("Expense added successfully (ID: " + expense.getId() + ")");
    }

    public void updateExpense(String Id, String description, double amount){
        Expense expense = findExpenseById(Id);
        assert expense != null;
        expense.setDescription(description);
        expense.setAmount(amount);
        System.out.println("Expense updated successfully");
    }

    public void deleteExpense(String Id){
        Expense expense = findExpenseById(Id);
        if (expense != null){
            expenses.remove(expense);
        }
        System.out.println("Expense deleted successfully");
    }

    private Expense findExpenseById(String id) {
        for (Expense expense : expenses) {
            if (expense.getId().equals(id)){
                return expense;
            }
        }
        return null;
    }

    public void summary(Integer month) {
        if (month == null) {
            double totalAmount = expenses.stream().mapToDouble(Expense::getAmount).sum();
            System.out.println("Total expenses: $" + totalAmount);
        } else {
            double monthlyAmount = expenses.stream()
                    .filter(expense -> expense.getDate().getMonthValue() == month)
                    .mapToDouble(Expense::getAmount)
                    .sum();

            String monthName = Month.of(month).name();
            String formattedMonthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1).toLowerCase();
            System.out.println("Total expenses for month " + formattedMonthName + ": $" + monthlyAmount);
        }
    }

    public void listExpenses() {
        // Print table header
        System.out.printf("%-36s %-12s %-15s %s%n", "ID", "Date", "Description", "Amount");
        // Print each record
        for (Expense expense : expenses) {
            System.out.printf("%-36s %-12s %-15s $%.2f%n",
                    expense.getId(), expense.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), expense.getDescription(), expense.getAmount());
        }
    }

    public void saveExpenses(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EXPENSES_PATH))) {
            writer.write("[\n");
            for (int i = 0; i < expenses.size(); i++) {
                writer.write(expenses.get(i).convertExpenseToJson());
                if (i < expenses.size() - 1){
                    writer.write(",\n");
                }
            }
            writer.write("\n]");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    public List<Expense> loadExpenses(){
        File file = new File(EXPENSES_PATH);
        if (!file.exists()){
            return new ArrayList<>();
        }

        List<Expense> stored_expense = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                sb.append(line);
            }
            //System.out.println(sb);
            parseExpensesFromJson(sb.toString(), stored_expense);
        } catch (IOException e) {
            System.err.println("Error loading expenses: " + e.getMessage());
        }
        return stored_expense;
    }

    private void parseExpensesFromJson(String json, List<Expense> stored_expense){
        json = json.trim();
        if (json.equals("[]")) return;
        json = json.substring(1, json.length() - 1);
        String[] expenses = json.split("},");
        for (String expenseJson : expenses) {
            if (!expenseJson.endsWith("}")){
                expenseJson = expenseJson + "}";
            }
            stored_expense.add(Expense.parseExpenseFromJson(expenseJson));
        }
    }
}
