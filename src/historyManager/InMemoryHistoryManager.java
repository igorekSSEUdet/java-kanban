package historyManager;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager<T> implements HistoryManager {
    private Node<T> first;
    private Node<T> last;
    private Map<Integer, Node> nodeMap = new HashMap<>();
    private List<Integer> orderOfGets = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (nodeMap.containsKey(task.getId())) {
            removeNode(task.getId());
        }
        nodeMap.put(task.getId(), addLast((T) task));

    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        for (Integer task : orderOfGets) {
            history.add((Task) nodeMap.get(task).item);
        }
        return history;
    }

    @Override
    public void remove(int id) {
        if (nodeMap.containsKey(id)) {
            removeNode(id);
            nodeMap.remove(id);
            orderOfGets.remove((Object) id);
        }
    }


    private Node<T> addLast(T element) {
        orderOfGets.add(((Task) element).getId());
        final Node<T> l = last;
        final Node<T> newNode = new Node<>(l, element, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;

        return newNode;
    }

    private boolean removeNode(int id) {

        orderOfGets.remove((Object) id);
        if (nodeMap.containsKey(id)) {
            unlink(nodeMap.get(id));
            return true;
        }
        return false;

    }


    private T unlink(Node<T> x) {
        // assert x != null;
        final T element = x.item;
        final Node<T> next = x.next;
        final Node<T> prev = x.prev;


        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        return element;
    }


    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

    }
}
