#### Java 堆 ：
   - 设置堆最小值 -Xms  
   - 设置堆最大值 -Xmx
   - 将堆的最小值参数与最大值参数设置为一样即可避免堆自动扩展（-Xms20m -Xmx20m）
   - 设置 -XX:+HeapDumpOnOutOfMemoryError 可以让虚拟机在出现内存溢出异常时 Dump 
        出当前的内存堆转储快照以便事后进行分析
#### java 栈：
   - 设置栈容量：-Xss
   - 如果线程请求的栈深度大于虚拟机所允许的最大深度，将抛出StackOverflowError异常。
   - 如果虚拟机在扩展栈时无法申请到足够的内存空间，则抛出 OutOfMemoryError 异常。
#### java 方法区 - 运行时常量池 (PermGen space 永久代)
  - -XX:PermSize
  - -XX:MaxPermSize
#### java 本机直接内存溢出
   - DirectMemory容量通过-XX:MaxDirectMemorySize指定
   - 如果不指定，则默认与Java堆的最大值（-Xmx）一样
    

        
        