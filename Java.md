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
