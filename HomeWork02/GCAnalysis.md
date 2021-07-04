#1.SerialGC
##特点：
1. 串行GC对年轻代使用mark-copy(标记-复制）算法，对老年代使用mark-sweep-compact(标记-清除-整理)算法
1. 两者都是单线程的垃圾收集器，不能进行并行处理，都会触发全线暂停，停止所有应用线程
##运行参数：
java -XX:+PrintGCDetails -Xloggc:gc.demo.log -Xmx512m -Xms512m -XX:+UseSerialGC GCLogAnalysis
##内存分配
1. Young:157248KB,eden:139776KB,from、to:17472KB,Old:349568KB
##垃圾回收情况
1. Young GC 16次，Full GC1次

#2.ParallelGC 
##特点：
1. 串行GC对年轻代使用mark-copy(标记-复制）算法，对老年代使用mark-sweep-compact(标记-清除-整理)算法，年轻代和老年代的垃圾回收都会触发SWT事件，暂停所有的应用线程来进行垃圾收集。两者在执行标记、复制/整理阶段使用多个线程，因此叫Parallel，通过并行执行，减少GC时间
##运行参数：
java -XX:+PrintGCDetails -Xloggc:gc.demo.log -Xmx512m -Xms512m -XX:+UseParallelGC GCLogAnalysis
##内存分配
1. Young:116736KB,eden:58880KB,from、to:57856KB,Old:349696KB
##垃圾回收情况
1. Young GC 28次，Full GC7次

#3.ConcMarkSweepGC
##特点：

1. 对年轻代采用并行STW方式的mark-copy算法，对老年代使用并发mark-sweep算法
1. 不对老年代进行整理，使用空闲列表来管理内存空间的回收
1. 在mark-sweep阶段的大部分工作和应用线程一起并发执行，没有明显的应用线程暂停，默认情况下CMS使用的并发线程数=CPU核心数的1/4
##运行参数：
java -XX:+PrintGCDetails -Xloggc:gc.demo.log -Xmx512m -Xms512m -XX:+UseConcMarkSweepGC GCLogAnalysis
##内存分配
1. Young:157248KB,eden:139776KB,from、to:17472,Old:349568KB
##垃圾回收情况
1. Young GC 19次，CMS GC7次

#4.G1GC
##特点：
1. 堆不再分成年轻代和老年代，而是划分为2048个存放对象的小块堆区域
，每个小块可能一会定义为Eden区，一会定义为Survivor区或者Old区
##运行参数：
java -XX:+PrintGC -Xloggc:gc.demo.log -Xmx512m -Xms512m -XX:+UseG1GC GCLogAnalysis
##垃圾回收情况
1. 纯年轻代转移暂停 25次，转移暂停：混合模式23次
##运行参数：
java -Xmx512m -Xms512m -XX:-UseAdaptiveSizePolicy -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -jar gateway-server-0.0.1-SNAPSHOT.jar
##内存分配
MaxHeapSize：512.0M，MaxNewSize:307.0M(MaxHeapSize*0.6),
Heap:512regions,EdenSpace:85regions,SurvivorSpace:22regions,OldSpace:2regions