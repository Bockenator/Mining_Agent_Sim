echo "Beginning Compile..."

#Compile Backend#
javac Backend/Observer.java
#Compile Environment
javac BackEnd/Environment/Asteroid.java
javac BackEnd/Environment/Environment.java

#Compile Logic
javac BackEnd/Logic/Action.java
javac BackEnd/Logic/Rule.java
javac BackEnd/Logic/Inference.java

#Compile Agent
javac BackEnd/Agent/Agent.java

#Compile Frontend
javac FrontEnd/Env2D.java
javac FrontEnd/Base.java

#Compile main
javac Main.java

echo "...Finished Compile"
echo "Running..."

#Run main
java Main

echo "...Finished"

#remove all class files to clean directory
cd ..
find . -type f -path "./src/*/*" -name "*.class" -delete
cd src
rm Main.class


echo "cleaned"
