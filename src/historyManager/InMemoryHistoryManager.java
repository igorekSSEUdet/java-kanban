package historyManager;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHistoryManager<T> implements HistoryManager {
    private HashMap<Integer, Node> map = new HashMap<>();
    ArrayList<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (map.containsKey(task.getId())) {
            removeNode(map.get(task.getId()));
        }
        map.put(task.getId(), addLast((T) task));

    }

    @Override
    public ArrayList<Task> getHistory() {
        return history;
    }

    @Override
    public void remove(int id) {
        if (map.containsKey(id)) {
            removeNode(map.get(id));
            map.remove(id);
        }
    }

    private Node<T> first;
    transient Node<T> last;

    Node<T> addLast(T element) {
        history.add((Task) element);
        final Node<T> l = last;
        final Node<T> newNode = new Node<>(l, element, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;

        return newNode;
    }

    public boolean removeNode(Node o) {
        history.remove((Task) o.item);
        if (o == null) {
            for (Node<T> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<T> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }


    T unlink(Node<T> x) {
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


    public static class Node<T> {
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
