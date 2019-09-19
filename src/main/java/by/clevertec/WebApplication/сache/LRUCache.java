package by.clevertec.WebApplication.сache;

import by.clevertec.WebApplication.constants.Constants;
import by.clevertec.WebApplication.dataSets.User;

import java.util.Comparator;
import java.util.Hashtable;
import java.util.Optional;
import java.util.PriorityQueue;

import static java.lang.Math.toIntExact;

public class LRUCache {
    private Hashtable<String, Optional<User>> cacheTable =  new Hashtable<>(Constants.sizeCache);
    private PriorityQueue<PriorityQueueObject> priorityQueue = new PriorityQueue<>(Constants.sizeCache, idComparator);

    public void addInCache(String id, Optional<User> user){
        if (priorityQueue.size() == Constants.sizeCache){
            String removKey = priorityQueue.poll().getKey();
            cacheTable.remove(removKey);
        }
        priorityQueue.add(new PriorityQueueObject(id, System.currentTimeMillis()));
        cacheTable.put(id, user);
    }

    public void replaceInPriorityQueue(String id){
        priorityQueue.remove(id);
        priorityQueue.add(new PriorityQueueObject(id, System.currentTimeMillis()));
    }

    private static Comparator<PriorityQueueObject> idComparator = (o1, o2) -> toIntExact (o1.getTime() - o2.getTime());

    public Hashtable<String, Optional<User>> getCacheTable() {
        return cacheTable;
    }

    public void setCacheTable(Hashtable<String, Optional<User>> cacheTable) {
        this.cacheTable = cacheTable;
    }

    public PriorityQueue getPriorityQueue() {
        return priorityQueue;
    }

    public void setPriorityQueue(PriorityQueue<PriorityQueueObject> priorityQueue) {
        this.priorityQueue = priorityQueue;
    }
}


