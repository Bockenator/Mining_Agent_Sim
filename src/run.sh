echo "Beginning Compile..."

#Compile Backend
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
