import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

// University Mystery Problem Constraint Satisfaction Problem (CSP) Implementation GROUP 13

/* Dependency we used : Choco Solver 4.10.13 (https://choco-solver.org)
   JAR: choco-solver-4.10.13-jar-with-dependencies.jar
   Added to classpath manually as a standalone library. */

public class UniversityMysteryProblem {
    public static void main(String[] args) {
        
        Model model = new Model("University Mystery CSP");

        // Subjectss
        IntVar subCS = model.intVar("CS", 1, 5);
        IntVar subMath = model.intVar("Math",1, 5);
        IntVar subPhilosophy = model.intVar("Philosophy", 1, 5);
        IntVar subHistory = model.intVar("History", 1, 5);
        IntVar subS5 = model.intVar("Subject5", 1, 5);

        // Decors
        IntVar decBlue = model.intVar("Blue", 1, 5);
        IntVar decRed = model.intVar("Red", 1, 5);
        IntVar decWhite = model.intVar("White", 1, 5);
        IntVar decYellow = model.intVar("Yellow", 1, 5);
        IntVar decGreen = model.intVar("Green", 1, 5);

        // Cars
        IntVar carTesla = model.intVar("Tesla", 1, 5);
        IntVar carBMW = model.intVar("BMW", 1, 5);
        IntVar carMercedes = model.intVar("Mercedes", 1, 5);
        IntVar carVolvo = model.intVar("Volvo",1, 5);
        IntVar carAudi = model.intVar("Audi",1, 5);

        // Research topics
        IntVar resAI = model.intVar("AI", 1, 5);
        IntVar resClimateChange = model.intVar("ClimateChange", 1, 5);
        IntVar resQuantumPhysics = model.intVar("QuantumPhysics", 1, 5);
        IntVar resNeuroscience = model.intVar("Neuroscience", 1, 5);
        IntVar resMedievalLiterature = model.intVar("MedievalLiterature", 1, 5);

        // Universities
        IntVar uniCambridge = model.intVar("Cambridge", 1, 5);
        IntVar uniOxford = model.intVar("Oxford", 1, 5);
        IntVar uniMIT = model.intVar("MIT", 1, 5);
        IntVar uniStanford = model.intVar("Stanford", 1, 5);
        IntVar uniHarvard = model.intVar("Harvard",1, 5);

        // Drinks
        IntVar drkEspresso = model.intVar("Espresso", 1, 5);
        IntVar drkHerbalTea = model.intVar("HerbalTea", 1, 5);
        IntVar drkGreenTea = model.intVar("GreenTea", 1, 5);
        IntVar drkBlackCoffee = model.intVar("BlackCoffee", 1, 5);
        IntVar drkD5 = model.intVar("Drink5", 1, 5);

        // AllDifferent constraints — one per attribute category
        model.allDifferent(subCS, subMath, subPhilosophy, subHistory, subS5).post();
        model.allDifferent(decBlue, decRed, decWhite, decYellow, decGreen).post();
        model.allDifferent(carTesla, carBMW, carMercedes, carVolvo, carAudi).post();
        model.allDifferent(resAI, resClimateChange, resQuantumPhysics, resNeuroscience, resMedievalLiterature).post();
        model.allDifferent(uniCambridge, uniOxford, uniMIT, uniStanford, uniHarvard).post();
        model.allDifferent(drkEspresso, drkHerbalTea, drkGreenTea, drkBlackCoffee, drkD5).post();

        // Explicit constraints from the problem statement
        subCS.eq(decBlue).post();    // C1
        uniOxford.eq(carTesla).post();  // C2
        resAI.eq(drkEspresso).post(); // C3
        uniCambridge.eq(1).post();  // C4
        carBMW.sub(decGreen).abs().eq(1).post(); // C5
        resClimateChange.eq(drkHerbalTea).post(); // C6
        subMath.eq(decRed).post(); // C7
        carMercedes.eq(resQuantumPhysics).post(); // C8
        drkGreenTea.eq(3).post(); // C9
        uniCambridge.sub(decYellow).abs().eq(1).post(); // C10
        carVolvo.eq(subPhilosophy).post(); // C11
        resNeuroscience.sub(carAudi).abs().eq(1).post(); // C12
        subHistory.eq(drkBlackCoffee).post(); // C13
        decWhite.eq(uniMIT).post(); // C14
        uniStanford.eq(uniHarvard.add(1).intVar()).post();// C15

        Solver solver = model.getSolver();
        solver.showDecisions(); // displays each variable assignment attempt
        Solution solution = solver.findSolution();
        solver.printStatistics(); // displayes nodes explored, backtracks, time taken

        if (solution != null) {
            System.out.println("\n SOLUTION WAS FOUND YAYY \n");
            System.out.println("Answer : The professor who researches Medieval Literature is in office "
                + solution.getIntVal(resMedievalLiterature) + "\n");

            System.out.println("Full variable assignment:");

            IntVar[] allVars = {
                subCS, subMath, subPhilosophy, subHistory, subS5,
                decBlue, decRed, decWhite, decYellow, decGreen,
                carTesla, carBMW, carMercedes, carVolvo, carAudi,
                resAI, resClimateChange, resQuantumPhysics, resNeuroscience, resMedievalLiterature,
                uniCambridge, uniOxford, uniMIT, uniStanford, uniHarvard,
                drkEspresso, drkHerbalTea, drkGreenTea, drkBlackCoffee, drkD5
            };

            for (IntVar v : allVars) {
                System.out.println(v.getName() + " -> Office " + solution.getIntVal(v));
            }

        } else {
            System.out.println("No solution found.");
        }
    }
}