import java.util.HashMap;
import java.util.Map;

public class ExpenseCliApp {

    public static void main(String[] args) {
        if (args.length<1){
            showUsage();
            return;
        }
        String command = args[0];
        ExpenseManager expenseManager = new ExpenseManager();
        switch (command) {
            case "add":
                try {
                    Map<String, String> addArgs = parseArgs(args);
                    String description = addArgs.get("--description");
                    String amountStr = addArgs.get("--amount");

                    if (description == null || amountStr == null) {
                        throw new IllegalArgumentException("Error: Missing --description or --amount.");
                    }

                    double amount = Double.parseDouble(amountStr);
                    expenseManager.addExpense(description, amount);
                } catch (NumberFormatException e) {
                    System.err.println("Error: Amount must be a valid number.");
                } catch (IllegalAccessError e) {
                    System.err.println(e.getMessage());
                }
                break;

            case "update":
                try {
                    Map<String, String> updateArgs = parseArgs(args);
                    String idToUpdate = updateArgs.get("--id");
                    String newDescription = updateArgs.get("--description");
                    String amountStr = updateArgs.get("--amount");

                    if (idToUpdate == null || newDescription == null || amountStr == null) {
                        throw new IllegalArgumentException("Error: Missing --id, --description, or --amount.");
                    }

                    double newAmount = Double.parseDouble(amountStr);
                    expenseManager.updateExpense(idToUpdate, newDescription, newAmount);
                } catch (NumberFormatException e) {
                    System.err.println("Error: Amount must be a valid number.");
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }
                break;

            case "delete":
                if (args.length < 3 || !args[1].equals("--id")) {
                    System.out.println("Error: You must specify --id <id> to delete.");
                } else {
                    String idToDelete = args[2];
                    expenseManager.deleteExpense(idToDelete);
                }
                break;

            case "list":
                expenseManager.listExpenses();
                break;

            case "summary":
                try {
                    if (args.length > 1 && "--month".equals(args[1])) {
                        int month = Integer.parseInt(args[2]);
                        if (month < 1 || month > 12) {
                            throw new IllegalArgumentException("Error: Month must be between 1 and 12.");
                        }
                        expenseManager.summary(month);
                    } else {
                        expenseManager.summary(null);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error: Month must be a valid number.");
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }
                break;

            default:
                System.out.println("Unknown command: " + command);
                showUsage();
                break;
        }
        expenseManager.saveExpenses();
    }

    public static Map<String, String> parseArgs(String[] argsArray) {
        Map<String, String> argsMap = new HashMap<>();
        for (int i = 1; i < argsArray.length; i++) {
            if (argsArray[i].startsWith("--")) {
                String key = argsArray[i];
                if (i + 1 < argsArray.length && !argsArray[i + 1].startsWith("--")) {
                    String value = argsArray[i + 1];
                    argsMap.put(key, value);
                    i++;
                }
            }
        }
        return argsMap;
    }

    private static void showUsage() {
        System.out.println("Usage:");
        System.out.println("  add --description <description> --amount <amount> - Add a new expense");
        System.out.println("  update --id <id> --description <description> --amount <amount> - Update an existing expense");
        System.out.println("  delete --id <id> - Delete an expense by ID");
        System.out.println("  list - List all expenses");
        System.out.println("  summary - Display the total expenses");
        System.out.println("  summary --month <month> - Display total expenses for a specific month");
    }

}
