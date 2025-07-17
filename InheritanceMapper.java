import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.eclipse.jdt.core.dom.*;

public class InheritanceMapper {
    private static HashMap<String, String> inheritanceRelations = new HashMap<String, String>();
    private static HashSet<String> parentClasses = new HashSet<String>();
    private static ArrayList<String> interfaces = new ArrayList<String>();

    public static List<List<String>> runInheritanceFinder(List<char[]> files) {
        for (char[] file : files) {
            ASTParser parser = ASTParser.newParser(AST.JLS18);
            parser.setSource(file);
            parser.setKind(ASTParser.K_COMPILATION_UNIT);

            CompilationUnit cu = (CompilationUnit) parser.createAST(null);
            InheritanceVisitor inheritanceVisitor = new InheritanceVisitor();
            cu.accept(inheritanceVisitor);
        }

        System.out.println("Interfaces:");
        printInterfaces(interfaces);
        System.out.println();
        System.out.println("Inheritance mappings:");
        printInheritanceList(calculateInheritanceRelations(parentClasses, inheritanceRelations));
        return calculateInheritanceRelations(parentClasses, inheritanceRelations);
    }

    private static List<List<String>> calculateInheritanceRelations(HashSet<String> parentClasses, HashMap<String, String> inheritanceRelations){
        List<List<String>> inheritanceList = new ArrayList<>();
        for (String parentClass : parentClasses) {
            List<String> tempList = new ArrayList<>();
            tempList.add(parentClass);
            inheritanceList.add(tempList);
        }

        for (List list : inheritanceList) {
            for (String key : inheritanceRelations.keySet()) {
                if (key.equals(list.get(0))) {
                    list.add(inheritanceRelations.get(key));
                }
            }
        }
        return inheritanceList;
    }



    private static void printInheritanceList(List<List<String>> inheritanceList){
        for (List list : inheritanceList) {
            System.out.println(list.get(0) + " is extended by: ");
            int i = 1;
            while (i < list.size()) {
                System.out.println(list.get(i));
                i++;
            }
        }
    }

    private static void printInterfaces(ArrayList<String> interfaces){
        for(int i = 0; i < interfaces.size(); i++) {
            System.out.println(interfaces.get(i).toString());
        }
    }

    private static class InheritanceVisitor extends ASTVisitor{
        public boolean visit(TypeDeclaration node){
            if(node.isInterface()){
                interfaces.add(node.getName().toString());
            }

            Type parentClass = node.getSuperclassType();
            if(parentClass != null) {
                inheritanceRelations.put(parentClass.toString(), node.getName().toString());
                parentClasses.add(parentClass.toString());
            }
            return true;
        }
    }
}
