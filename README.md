# Java Study

## Common Algorithms & Data Structures

* Trie - common use cases include autocomplete or typeahead, where based on an input prefix string, a list of possible values are returned
* Sketch algorithms - The algorithms provide an approximate results with a significant less storage requirement
  * Bloom Filter - 
    * Used to find out if an element is present in a given list
    * False positives are possible
    * Used by certain DBMS to reduce disk access
    * Also used by social networking sites to remove viewed items from the user feed
    * Fast hash functions like Murmur3 is used
    * One possible way to create multiple hash functions is to break the output of a single hash function into multiple parts. E.g. 128 bit output of a hash function can be split into 4 32-bit parts
  * Cuckoo filter - 
    * Similar to Bloom filter with certain optimizations
  * Hyperloglog - 
    * Used to find an approximate cardinality or, in other words, number of unique values
    * E.g. No. of unique visitors in a web page
  * Count-Min Sketch -
    * Used to find the count of events
    * E.g. count of download of a software package
  * Top-K -
    * Top K frequent items
* String search algorithms - 
  * Rabin Karp
  * KMP
  * Boyer-Moore

## Collections

* Collections.sort() and Arrays.sort() for objects use TimSort, a variation of merge sort. Although, merge sort is slower than quick sort, it is used here because it is a stable algorithm. Time complexity **O(n log(n))**
* Arrays.sort() does Dual-Pivot quick sort for native data types. Time complexity **O(n log(n))**
* LinkedList implementation is a doubly linked list
* Red Black Tree, if used concurrently, needs a mutex lock on a large portion of the tree when a tree modification occurs and the tree needs to rebalance. In comparison, in a Skip List, for any modification, only the nodes directly connected to the affected node need to be locked. Hence, ConcurrentSkipListMap uses a Skip List instead of a Red Black Tree
* ArrayList operations read, insertion at end or deletion at end take constant time unless the array needs to be reallocated for accommodating more elements than its current size can hold
* Insertion or Deletion in the middle of an ArrayList has linear time complexity
* ConcurrentSkipListMap & TreeMap both sort the keys based on the natural order of the key objects or the Comparator passed at the time of Map creation
* LinkedHashMap's method removeEldestEntry can be overridden to define a policy for removing the least recently used element when implementing a LRU cache
* HashMap provides constant time performance for get and put operations assuming the hash function is good enough to distribute the elements properly
* ConcurrentSkipListMap provides log(n) time cost for containsKey, get, put and remove operations
* TreeMap provides guaranteed log(n) time cost for containsKey, get, put and remove operations
* ConcurrentSkipListMap and TreeMap implement NavigableMap interface. Therefore, they provide methods to quickly access first entry, last entry, to remove first entry, last entry etc.
* IdentityHashmap uses reference equality instead of Object equality when comparing keys and values
* IdentityHashMap is built on top of HashTable
* The underlying data structure of Map is an array of linked list or array of Binary Search Tree
* WeakReference can be used for caching. If no other strong reference is found, the object will be garbage collected immediately in the next cycle
* SoftReference can be used for caching. If no other strong reference is found for an object, the object will be garbage collected only when there is a need to free up memory
* HashMap is rehashed when the number of elements is greater than the product of current capacity and load factor
* The default capacity (number of bucket) of HashMap is 16
* The default load factor of HashMap is 0.75
* The default concurrency of HashMap is 16
* The default initial size of ArrayList is 10
* The underlying data structure of ConcurrentSkipListMap is skip list
* The underlying data structure of ArrayList is Array
* The underlying data structure of TreeMap is Red-Black Tree
* Collection implementations in java.util.concurrent package usually return weakly consistent iterators that iterate over a clone of the collection and hence it doesn't throw ConcurrentModificationException for simultaneous structural changes, but the iterator may not reflect the most up-to-date state of the collection and also there is an overhead of cloning the collection while returning iterator
* Use removeIf() of List or remove() of Iterator to avoid ConcurrentModificationException
* Collection implementations in the java.util package usually return fail-fast iterators which throws ConcurrentModificationException if there is any structural modification during iteration, unless iterator.remove() or removeIf() methods are used
* LinkedHashMap can be used to copy a map with the same order as the original
* LinkedHashMap's map access order type can be used for implementing LRU cache
* The underlying data structure of LinkedHashMap is doubly linked list
* LinkedHashMap can be access order type or insertion order type
* 


## Multi-Threading

* java.lang.InheritableThreadLocal<T> extends ThreadLocal to provide inheritance of values in the child thread
* java.lang.InheritableThreadLocal<T> extends ThreadLocal to provide inheritance of values in the child thread
* 

## Streams

* Stream.Builder<T> is used to build a stream by adding one element at a time without the overhead of using an ArrayList as a buffer
* 

## Others

* Array objects cannot have user defined serialVersionUID. Hence the requirement of matching serialVersionUID is waived for arrays
* Any class that need to be serializable (written to a file or transferred over a network) must implement the marker interface Serializable, otherwise NotSerializableException will be thrown
* If serialVersionUID is not provided in a serializable class, the compiler will automatically generated one. But since the value depends on the compiler implementation, it is recommended that the class author provides the value.
* If during deserialization of an object, the JVM finds that the serialVersionUID in the object in question is not the same with that of the loaded class in the receiver JVM, InvalidClassException will be thrown
* Every serializable class must have a static final long value serialVersionUID. It can have any access specifier. 
* If the clone() method is called on an object that does not implement the Cloneable interface, exception CloneNotSupported will be thrown
* Cloneable interface does not have a clone() method. It is a marker interface that indicates to the Object.clone() method that the class instance can be copied field-for-field
* Java 8 Optional to avoid !=null
* Java uses Pass By Value


## Some of my StackOverflow Answers

* [Why Are HashMaps Implemented Using Powers of Two?](https://stackoverflow.com/a/53526992/1235935)
* [How to encrypt String in Java](https://stackoverflow.com/a/53015144/1235935)
* [SSLServerSocket and certificate setup](https://stackoverflow.com/a/53325115/1235935)

### Avoid Returning null

Instead of returning null values from a function consider returning immutable empty collections created by the `empty*` methods in the `Collections` class, or the `Optional<T>` which was introduced in Java 8. However, `Optional<T>` adds some overhead.

### Immutable Collections For Efficiency

In Java 9, `List`, `Map` and `Set` interfaces provide the factory method `of()` to create immutable lists, maps or sets respectively with known values. This would be efficient when the values are read frequently and modified rarely. Moreover, if the contents are also immutable, the entire collection would be automatically thread safe without the need of any kind of locking.

### Bit Oprations are Faster than Division

a mod 2^i = a & (2^i–1)

e.g. 44 mod 32 = 0010 1100 & 0001 1111 = 0000 1100 = 12

### Underlying Data Structures of Collections

* ArrayList is based on arrays.
* HashMap is based on arrays of Red Black Tree
* HashSet is based on HashMap
* LinkedHashMap is based on Doubly Linked List
* TreeMap is based on Red Black Tree

### LinkedHashMap

LinkedHashMap can maintain a specific order of the entries (predictable iteraion order)
- Insertion order
- Access order

The underlying data structure is a doubly linked list.

If the map is constructed to preserve access order, every `get()` call will increase the modCount (which is used by the Iterator to throw ConcurrentMModificationException) and also move the entry before the current head of the LinkedList. This functionality can be used to implement LRU cache, provided the `removeEldestEntry()` is overridden to implement a cache eviction policy.

### Unicode Charset

UTF-8 & UTF-16 are both variable length Unicode encoding. Only UTF-32 is a fixed length Unicode encoding and it takes 4 bytes to encode each character. Hence, UTF-32 is space inefficient. 

Java (upto version 8) internally uses UTF-16 to encode `String` characters. UTF-16 a subset of characters in 2 bytes, while the other characters need 4 bytes. Since the primitive `char` datatype is always 2 bytes, therefore to encode some of the unicode character (e.g. an emoji character) in a `String`, 2 `char` elements are required. On the other hand, all ASCII characters and some other characters have smaller sized Unicode encoding in UTF-16 and hence they need only 1 `char`. Therefore, it is better to avoid the String functions like `charAt` or any other function that doesn't respect the fact that characters may span 2 `char` elements.

    String str="😂s🍆";
    System.out.println(str.length()); // 5
    System.out.println(str.substring(0, str.indexOf('s'))); // 😂

Compact String is introduced since Java 9 which encodes a `String` in Latin-1, also called ISO-8859-1 as long as all characters of the `String` can be encoded in a single byte, otherwise UTF-16 will be used as usual.

### JSON Web Token (JWT) Digital Signature Algorithm

JSON Web Token (JWT) are usually digitally signed by HMAC SHA256

### hashCode() implementation of String class

A decent hash code of any object can be generated as:

    hashCode = 1;
    hashCode = 31 * hashCode + a.hashCode();
    hashCode = 31 * hashCode + b.hashCode();

where a, b are member variables;

A nice property of 31 is that the multiplication can be replaced by a shift and a subtraction for better performance: `31 * i == (i << 5) - i`. Modern VMs do this sort of optimization automatically.

### Convert String to bytes[] & Vice Versa

    byte[] b = string.getBytes(StandardCharsets.UTF_8);
    
    new String(b, StandardCharsets.UTF_8);

### Growth of Arraylist

ArrayList grows by 50% of its current size. The increment is size is calculated by shifting the bits of the current size by 1 to the right.

### Growth of StringBuilder

`StringBuilder` grows by the current size + additional 2 bytes. So, the new size would be (current size) * 2 + 2
