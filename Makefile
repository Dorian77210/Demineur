SRC_DIR := Src/
BIN_DIR := Bin/
SAV_DIR := Save/
DOC_DIR := Docs/
FLAGS := -d $(BIN_DIR) -sourcepath $(SRC_DIR) -classpath $(BIN_DIR)
JC := javac
JEXE := java -classpath $(BIN_DIR)
JDOC := javadoc
RM := rm -f
MKDIR := mkdir

#Class files required
OBJS := $(BIN_DIR)Main.class \
		$(BIN_DIR)SelectionView.class \
		$(BIN_DIR)SelectionController.class \
		$(BIN_DIR)SelectionModel.class \
		$(BIN_DIR)GameWindow.class \
		$(BIN_DIR)GameController.class \
		$(BIN_DIR)GameModel.class \
		$(BIN_DIR)HomeView.class \
		$(BIN_DIR)HomeController.class \
		$(BIN_DIR)HomeModel.class \
		$(BIN_DIR)DataSession.class \
		$(BIN_DIR)Session.class \
		$(BIN_DIR)VisualClock.class \
		$(BIN_DIR)SessionController.class \
		$(BIN_DIR)Board.class \
		$(BIN_DIR)RandomPosition.class \
		$(BIN_DIR)BoardController.class \
		$(BIN_DIR)Cell.class \
		$(BIN_DIR)ViewState.class

#Main target, build bin and docs
all: $(BIN_DIR) $(SAV_DIR) $(OBJS) $(DOC) doc #not PHONY 

#Create bin and save folder
$(BIN_DIR):
	$(MKDIR) $(BIN_DIR)
$(SAV_DIR):
	$(MKDIR) $(SAV_DIR)


$(BIN_DIR)Main.class: $(SRC_DIR)/Main.java $(BIN_DIR)GameWindow.class
	$(JC) $(FLAGS) $(SRC_DIR)Main.java

$(BIN_DIR)SelectionView.class: $(SRC_DIR)/SelectionView.java $(BIN_DIR)SelectionController.class $(BIN_DIR)SelectionModel.class
	$(JC) $(FLAGS) $(SRC_DIR)SelectionView.java

$(BIN_DIR)SelectionController.class: $(SRC_DIR)/SelectionController.java $(BIN_DIR)SelectionModel.class
	$(JC) $(FLAGS) $(SRC_DIR)SelectionController.java

$(BIN_DIR)SelectionModel.class: $(SRC_DIR)/SelectionModel.java
	$(JC) $(FLAGS) $(SRC_DIR)SelectionModel.java

$(BIN_DIR)GameWindow.class: $(SRC_DIR)/GameWindow.java $(BIN_DIR)GameModel.class $(BIN_DIR)GameController.class
	$(JC) $(FLAGS) $(SRC_DIR)GameWindow.java

$(BIN_DIR)GameController.class: $(SRC_DIR)/GameController.java $(BIN_DIR)GameModel.class
	$(JC) $(FLAGS) $(SRC_DIR)GameController.java

$(BIN_DIR)GameModel.class: $(SRC_DIR)/GameModel.java $(BIN_DIR)HomeView.class $(BIN_DIR)SelectionView.class $(BIN_DIR)ViewState.class $(BIN_DIR)Session.class $(BIN_DIR)DataSession.class
	$(JC) $(FLAGS) $(SRC_DIR)GameModel.java

$(BIN_DIR)HomeView.class: $(SRC_DIR)/HomeView.java $(BIN_DIR)HomeController.class $(BIN_DIR)HomeModel.class
	$(JC) $(FLAGS) $(SRC_DIR)HomeView.java

$(BIN_DIR)HomeController.class: $(SRC_DIR)/HomeController.java $(BIN_DIR)HomeModel.class
	$(JC) $(FLAGS) $(SRC_DIR)HomeController.java

$(BIN_DIR)HomeModel.class: $(SRC_DIR)/HomeModel.java
	$(JC) $(FLAGS) $(SRC_DIR)HomeModel.java

$(BIN_DIR)DataSession.class: $(SRC_DIR)/DataSession.java $(BIN_DIR)Board.class
	$(JC) $(FLAGS) $(SRC_DIR)DataSession.java

$(BIN_DIR)Session.class: $(SRC_DIR)/Session.java $(BIN_DIR)Board.class $(BIN_DIR)DataSession.class $(BIN_DIR)SessionController.class $(BIN_DIR)VisualClock.class $(BIN_DIR)Cell.class
	$(JC) $(FLAGS) $(SRC_DIR)Session.java

$(BIN_DIR)VisualClock.class: $(SRC_DIR)/VisualClock.java
	$(JC) $(FLAGS) $(SRC_DIR)VisualClock.java

$(BIN_DIR)SessionController.class: $(SRC_DIR)/SessionController.java
	$(JC) $(FLAGS) $(SRC_DIR)SessionController.java

$(BIN_DIR)Board.class: $(SRC_DIR)/Board.java $(BIN_DIR)BoardController.class $(BIN_DIR)RandomPosition.class $(BIN_DIR)Cell.class
	$(JC) $(FLAGS) $(SRC_DIR)Board.java

$(BIN_DIR)RandomPosition.class: $(SRC_DIR)/RandomPosition.java
	$(JC) $(FLAGS) $(SRC_DIR)RandomPosition.java

$(BIN_DIR)BoardController.class: $(SRC_DIR)/BoardController.java
	$(JC) $(FLAGS) $(SRC_DIR)BoardController.java

$(BIN_DIR)Cell.class: $(SRC_DIR)/Cell.java
	$(JC) $(FLAGS) $(SRC_DIR)Cell.java

$(BIN_DIR)ViewState.class: $(SRC_DIR)/ViewState.java
	$(JC) $(FLAGS) $(SRC_DIR)ViewState.java

#Launch the game
test:
	$(JEXE) Main

#Remove all compiled file from bin folder
clean: docclean
	$(RM) $(BIN_DIR)*.class

#Generate javadoc
doc:
	$(JDOC) -d $(DOC_DIR) $(SRC_DIR)*.java

#Remove javadoc
docclean:
	$(RM) $(DOC_DIR)*.html
	$(RM) $(DOC_DIR)*.js
	$(RM) $(DOC_DIR)*.css
	$(RM) $(DOC_DIR)package-list

.PHONY: clean test doc#not files