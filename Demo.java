package hMap;

import java.util.Scanner;
import java.util.Set;

import hMap.HashMap.Entry;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;


@SuppressWarnings("unused")
class HashMap<K,V> {
	private Entry<K,V>[] table; 
    private int capacity= 4;
    private int size=0;
    static final int MAXIMUM_CAPACITY = 32;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    int threshold;
    final float loadFactor;
    @SuppressWarnings("unchecked")
	public HashMap(){
        table = new Entry[capacity];
        loadFactor = DEFAULT_LOAD_FACTOR;
        threshold = (int)(capacity*loadFactor);
     }
    @SuppressWarnings("unchecked")
	public HashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                                               initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                                               loadFactor);
        this.capacity = tableSizeFor(initialCapacity);
        this.loadFactor = loadFactor;
        this.threshold = (int) (this.capacity*loadFactor);
        
        table =new Entry[this.capacity];
    }

   
    public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
    public int getCapacity() {
    	return this.capacity;
    }
    static class Entry<K, V> {
        K key;
        V value;
        Entry<K,V> next;
        
    
        public Entry(K key, V value, Entry<K,V> next){
            this.key = key;
            this.value = value;
            this.next = next;
        }
        public K getKey() {
        	return this.key;
        }
        public V getValue() {
        	return this.value;
        }
    }
    public void display(){
    	System.out.print("[");
        for(int i=0;i<capacity;i++){
            if(table[i]!=null){
                   Entry<K, V> entry=table[i];
                   while(entry!=null){
                         System.out.print(entry.getKey()+"="+entry.getValue()+", ");
                         entry=entry.next;
                   }
            }
        }
        System.out.println("]");
     
     }
    @SuppressWarnings("unchecked")
	public Object clone() {
    	HashMap<K,V> result = new HashMap<K,V>(this.capacity,this.loadFactor);
    	result.table= new Entry[table.length];
    	for(int i=0;i<capacity;i++){
            if(table[i]!=null){
                   Entry<K, V> entry=table[i];
                   while(entry!=null){
                         result.put(entry.key, entry.value);
                         entry=entry.next;
                   }
            }
        }  
    	
    	return result;
    	
    }
    private int hash(K key){
        return (Math.abs(key.hashCode()) & (capacity-1));
    }
    public Entry<K,V> getNode(int hash,K fKey) {
    	
    	for(Entry<K,V> e = table[hash]; e!=null;e=e.next) {
			if(e.key==fKey) {
				return e;
				
				}
    		}
    	return null;
    }
    public boolean containsKey(K fKey) {
    	return ((getNode(hash(fKey),fKey)!= null) ? true:false);
    	
    }
    public boolean containsValue(Object value) {
        Entry<K,V>[] tab; V v;
        if ((tab = table) != null && size > 0) {
            for (int i = 0; i < tab.length; ++i) {
                for (Entry<K,V> e = tab[i]; e != null; e = e.next) {
                    if ((v = e.value) == value ||
                        (value != null && value.equals(v)))
                        return true;
                }
            }
        }
        return false;
    }
   public V remove(K fKey) {
	   int hash = hash(fKey);
	   //V v;
	   Entry<K,V> previous = null;
       Entry<K,V> current = table[hash];
       
       while(current != null){ 
          if(current.key==fKey){               
              if(previous==null){ 
            	    
                    table[hash]=current.next;
                    
              }
              else{
                    previous.next=current.next;
                    
              }
              size--;
              return current.value;
          }
          previous=current;
          current = current.next;
         }
       return null;
	   
   }
    
    public V get(K fKey) {
    	Entry<K,V> e = getNode(hash(fKey),fKey);
    	if(e==null) {
    	System.out.println("invalid key !! key not found");
    	return null;
    	}
    	else {
    		return e.value;
    	}
    }
    public V replace(K key, V value) {
        Entry<K,V> e;
        if ((e = getNode(hash(key), key)) != null) {
            V oldValue = e.value;
            e.value = value;
            return oldValue;
        }
        return null;
    }
    public boolean replace(K key, V oldValue, V newValue) {
        Entry<K,V> e; V v;
        if ((e = getNode(hash(key), key)) != null &&
            ((v = e.value) == oldValue || (v != null && v.equals(oldValue)))) {
            e.value = newValue;
            return true;
        }
        return false;
    }

    public void put(K cKey,V cValue) {
    	if(cKey==null) {
    		putForNullKey(cValue);
    		return;
    	}
    		
    	int hash = hash(cKey);
    	if(table[hash]==null) {
    		createEntry(hash,cKey,cValue);
    		return;
    		
    	}
    	else {
    		Entry<K,V> e = getNode(hash(cKey),cKey);
    		 if(e!=null) 
    		 {
    		   e.value = cValue;
    		 }
    		 else {
    			 createEntry(hash,cKey,cValue);
    		 }
    	}
    	
    	
    }
    private void putForNullKey(V value) {
        for (Entry<K,V> e = table[0]; e != null; e = e.next) {
            if (e.key == null) {
               
                e.value = value;
                return;
                
            }
        }
       
        createEntry(0, null, value);
        return;
    }
    
    private void createEntry(int hash, K key, V value) {
        Entry<K,V> e = table[hash];
        table[hash] = new Entry<>(key, value, e);
        if(size++>=threshold)
        {resize(2*table.length);}
    }
    private void resize(int newCapacity) {
       System.out.println("inside resize");
       
        if (capacity == MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }
        capacity = newCapacity;

        @SuppressWarnings("unchecked")
		Entry<K,V>[] newTable = new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int)(newCapacity * loadFactor);
    }
   private void transfer(Entry<K,V>[] newTable) {
        Entry<K,V>[] src = table;
        for (int j = 0; j < src.length; j++) {
            Entry<K,V> e = src[j];
            if (e != null) {
                src[j] = null;
                do {
                    Entry<K,V> next = e.next;
                    int i = hash(e.key);
                    e.next = newTable[i];
                    newTable[i] = e;
                    e = next;
                } while (e != null);
            }
         }
    }
   
public Collection<V> values() {
	   Set<Entry<K,V>> es = this.entrySet();
	   Collection<V> vs = new ArrayList<V>();
		for(Entry<K,V> e :es) {
			vs.add(e.value);
			
		}
		return vs;
  }
public Set<K> keySet(){
	Set<Entry<K,V>> es = this.entrySet();
	Set<K> kSet = new HashSet<K>();
	for(Entry<K,V> e :es) {
		kSet.add(e.key);
		
	}
	return kSet;
}

public Set<Entry<K,V>> entrySet(){
	Set<Entry<K,V>> hash_Set = new HashSet<Entry<K,V>>();
	for(int i=0;i<capacity;i++){
        if(table[i]!=null){
               Entry<K, V> entry=table[i];
               while(entry!=null){
                     hash_Set.add(entry);
                     entry=entry.next;
               }
        }
    }
	return hash_Set;
	
	
}
    public int size() {
    	return size;
    }
    public boolean isEmpty() {
    	return (size==0);
    }
    public void clear() {
        Entry<K,V>[] t = table;
        for (int i = 0; i < t.length; i++)
            t[i] = null;
        size = 0;
    }

	
}
public class Demo{
@SuppressWarnings({ "rawtypes", "unchecked" })
public static void main(String[] args) {
	// TODO Auto-generated method stub
	HashMap <Integer,String> m = new HashMap<Integer,String>();
	HashMap <Integer,String> t = new HashMap<Integer,String>();
	HashMap <String,Integer> si = new HashMap<String,Integer>();
	int choice=5,k;
	String s;
	boolean itr =true;
	Scanner in = new Scanner(System.in);
	m.put(20, "Happy");
	m.put(23, "Birthday");
	
	m.put(22, "cco");
	m.put(21, null);
	si.put(null, 23);
	m.put(24, "cto");
	
	m.put(25, "ceo");
	
	while(itr) {
		System.out.println("1.put 2.get 3.display 4.remove 5.size \n"
				+ "6.isEmpty 7.containsKey 8.containsValue\n"
				+ "9.clear 10. clone 11.replace with new value\n"
				+ "12.replace in the place of new value 13.exit");
		choice = in.nextInt();
		switch(choice) {
		case 1:
			System.out.println("enter the key");
			  k = in.nextInt();
			  System.out.println("enter the value");
			  s = in.next();
			  m.put(k, s);
			 break;
		case 2:
			 System.out.println("enter the key");
			  k = in.nextInt();
			 System.out.println("value "+m.get(k));
			 break;
		case 3:
			 System.out.println("Maps are");
			 m.display();
			 System.out.println("Values are"+m.values());
			 System.out.println("Keys are"+m.keySet());
			 break;
		case 4:
			 System.out.println("enter the key");
			  k = in.nextInt();
			 System.out.println("value removed"+m.remove(k));
			 break;
			 
		case 5:
			System.out.println("capacity"+m.getCapacity());
			 System.out.println("no of mappping"+m.size());
			 break;
		case 6:
			 System.out.println("empty check"+m.isEmpty());
			 break;
		case 7:
			 System.out.println("enter the key");
			  k = in.nextInt();
			 System.out.println("presence of "+k+" "+m.containsKey(k));
			 break;
		case 8:
			 System.out.println("enter the value");
			  s = in.next();
			 System.out.println("presence of "+s+m.containsValue(s));
			 break;
		case 9:
			 m.clear();
			 break;
		case 10:
			t=(HashMap)(m.clone());
			t.display();
			System.out.println("Size "+t.size());
			break;
		case 11:
			System.out.println("enter the key");
			  k = in.nextInt();
			System.out.println("enter the value");
			  s = in.next();
			System.out.println("Replaced value "+m.replace(k,s));
			break;
		case 12:
			String newVal;
			System.out.println("enter the key");
			  k = in.nextInt();
			System.out.println("enter the oldvalue");
			  s = in.next();
			System.out.println("enter the new value");
			  newVal = in.next();
			  if(m.replace(k,s,newVal)) {
				  System.out.println("value replaced");
			  }
			  else {
				  System.out.println("no map is found with given key-value pair");
			  }
			
			
			 break;
		case 13:
			itr=false;
			 break;
		}
	}
	t.clear();
	/*Set<Entry<Integer,String>> es = m.entrySet();
	for(Entry<Integer,String> e :es) {
		System.out.println(e.getKey()+"--"+e.getValue());
		
	}*/
	m.clear();
in.close();
}
}

