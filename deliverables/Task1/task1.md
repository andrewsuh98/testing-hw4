# Task 1

### Question 1

To achieve a good test suite for unit testing, it is critical to write effective test cases. With ineffective test cases, it is easy to overlook faults with unit testing. Below is an example from **Exercise 3**. The ```testSort()``` function tests a sorting function. However, this test case only checks if the first name was correctly sorted, resulting in a _false positive_. Although is a very trivial example, much more complex and difficult nuances come up during real world unit testing. On the other hand, GUI testing tests for end-user interactions, and therefore with thorough testing, many bugs show itself as the output.

```java
@Test
public void testSort() {
   names.add ("Laura"); 
   names.add ("Han"); 
   names.add ("Alex"); 
   names.add ("Ashley"); 
   names.sort(); 
   assertTrue ("Sort method", names.getFirst().equals("Alex"));
}
```
### Question 2

