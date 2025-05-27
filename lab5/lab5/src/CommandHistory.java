import java.util.Stack;

public class CommandHistory {
    private Stack<ICommand> history = new Stack<>();

    public void push(ICommand command) {
        history.push(command);
        printStack("После добавления команды:");
    }

    public ICommand pop() {
        ICommand command = history.pop();
        printStack("После извлечения команды:");
        return command;
    }

    public boolean isEmpty() {
        return history.isEmpty();
    }

    // Новый метод для печати стека
    public void printStack(String message) {
        System.out.println("\n" + message);
        if (history.isEmpty()) {
            System.out.println("Стек команд пуст");
            return;
        }

        System.out.println("Текущий стек команд (сверху вниз):");
        for (int i = history.size() - 1; i >= 0; i--) {
            ICommand cmd = history.get(i);
            System.out.printf("[%d] %s%n", i, cmd.toString());
        }
    }
}
