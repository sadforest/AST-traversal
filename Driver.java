import org.eclipse.jdt.core.dom.*;

import java.util.*;

public class Driver {
    private static Queue<List<List<String>>> inheritanceQueue = new LinkedList<List<List<String>>>();
    private static Queue<HashMap<String, Integer>> methodQueue = new LinkedList<HashMap<String, Integer>>();

    public static void main(String[] args) throws Exception {
        String javaDir = "";
        Scanner userInput = new Scanner(System.in);


        List<char[]> javaFiles = FileFinder.getFilesInDirectory(javaDir);
        runInheritanceMapper(javaFiles);
        runMethodMapper(javaFiles);
    }

    public static void runInheritanceMapper(List<char[]> files) {
        inheritanceQueue.add(InheritanceMapper.runInheritanceFinder(files));
    }

    public static void runMethodMapper(List<char[]> files) {
        HashMap<String, Integer> methodMap = MethodMapper.mapMethods(files);
        int methodCount = MethodMapper.getMethodCount();
        List<String> unusedMethods = MethodMapper.getUnusedMethods();

        methodQueue.add(methodMap);

        System.out.println("The total number of non-constructor methods declared in this version is: " + methodCount);
        System.out.println("All methods that were invoked and their respective invocation counts:");

        for (String key : methodMap.keySet()) {
            System.out.println(key + " - " + methodMap.get(key));
        }

        System.out.println("The following methods were declared but not used: ");

        for (String unusedMethod : unusedMethods) {
            System.out.println(unusedMethod);

        }
    }
}
