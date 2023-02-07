package managers.historyManager;


import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private Node first;
    private Node last;

    Map<Integer, Node> nodeMap = new HashMap<>();


    private List<Task> getTasks() {
        List<Task> history = new ArrayList<>();
        Node node = first;
        while (node != null) {
            history.add(node.item);
            node = node.next;
        }
        return history;
    }

    @Override
    public void add(Task task) {

        if (nodeMap.containsKey(task.getId())) {
            removeNode(task.getId());
        }
        nodeMap.put(task.getId(), linkLast(task));

    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        if (nodeMap.containsKey(id)) {
            removeNode(id);
            nodeMap.remove(id);
        }

    }


    private Node linkLast(Task element) {

        final Node l = last;
        final Node newNode = new Node(element, null, l);
        last = newNode;
        if (l == null)
            first = newNode;
        else {
            l.next = newNode;
        }

        return newNode;
    }

    private void removeNode(int id) {

        if (nodeMap.containsKey(id)) {

            unlink(nodeMap.get(id));
        }

    }


    private void unlink(Node x) {
        // assert x != null;
        final Task element = x.item;
        final Node next = x.next;
        final Node prev = x.prev;


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
    }


    private static class Node {
        Task item;
        Node next;
        Node prev;


        public Node(Task item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "item=" + item +
                    ", next=" + next +
                    ", prev=" + prev +
                    '}';
        }
    }
}