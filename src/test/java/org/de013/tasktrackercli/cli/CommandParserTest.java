package org.de013.tasktrackercli.cli;

import org.de013.tasktrackercli.cli.command.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {
    
    // ===== TEST PARSE ADD COMMAND =====
    
    @Test
    void testParseAddCommand_WithValidDescription_ShouldReturnAddCommand() {
        // ARRANGE
        String[] args = {"add", "Buy groceries"};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT
        assertNotNull(command);
        assertTrue(command instanceof AddCommand);
    }
    
    @Test
    void testParseAddCommand_WithMissingDescription_ShouldReturnNull() {
        // ARRANGE: Thiếu description
        String[] args = {"add"};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT: Validation fail → return null
        assertNull(command);
    }
    
    @Test
    void testParseAddCommand_WithEmptyDescription_ShouldReturnNull() {
        // ARRANGE: Description rỗng
        String[] args = {"add", "   "};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT
        assertNull(command);
    }
    
    // ===== TEST PARSE UPDATE COMMAND =====
    
    @Test
    void testParseUpdateCommand_WithValidArgs_ShouldReturnUpdateCommand() {
        // ARRANGE
        String[] args = {"update", "1", "New description"};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT
        assertNotNull(command);
        assertTrue(command instanceof UpdateCommand);
    }
    
    @Test
    void testParseUpdateCommand_WithMissingDescription_ShouldReturnNull() {
        // ARRANGE: Thiếu description
        String[] args = {"update", "1"};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT
        assertNull(command);
    }
    
    @Test
    void testParseUpdateCommand_WithInvalidId_ShouldReturnNull() {
        // ARRANGE: ID không phải số
        String[] args = {"update", "abc", "New description"};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT
        assertNull(command);
    }
    
    @Test
    void testParseUpdateCommand_WithNegativeId_ShouldReturnNull() {
        // ARRANGE: ID âm
        String[] args = {"update", "-1", "New description"};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT
        assertNull(command);
    }
    
    // ===== TEST PARSE DELETE COMMAND =====
    
    @Test
    void testParseDeleteCommand_WithValidId_ShouldReturnDeleteCommand() {
        // ARRANGE
        String[] args = {"delete", "1"};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT
        assertNotNull(command);
        assertTrue(command instanceof DeleteCommand);
    }
    
    @Test
    void testParseDeleteCommand_WithMissingId_ShouldReturnNull() {
        // ARRANGE
        String[] args = {"delete"};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT
        assertNull(command);
    }
    
    @Test
    void testParseDeleteCommand_WithInvalidId_ShouldReturnNull() {
        // ARRANGE
        String[] args = {"delete", "not_a_number"};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT
        assertNull(command);
    }
    
    // ===== TEST PARSE MARK COMMANDS =====
    
    @Test
    void testParseMarkInProgressCommand_WithValidId_ShouldReturnCommand() {
        // ARRANGE
        String[] args = {"mark-in-progress", "1"};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT
        assertNotNull(command);
        assertTrue(command instanceof MarkInProgressCommand);
    }
    
    @Test
    void testParseMarkDoneCommand_WithValidId_ShouldReturnCommand() {
        // ARRANGE
        String[] args = {"mark-done", "1"};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT
        assertNotNull(command);
        assertTrue(command instanceof MarkDoneCommand);
    }
    
    @Test
    void testParseMarkCommand_WithMissingId_ShouldReturnNull() {
        // ARRANGE
        String[] args = {"mark-in-progress"};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT
        assertNull(command);
    }
    
    // ===== TEST PARSE LIST COMMAND =====
    
    @Test
    void testParseListCommand_WithoutStatus_ShouldReturnListCommand() {
        // ARRANGE
        String[] args = {"list"};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT
        assertNotNull(command);
        assertTrue(command instanceof ListCommand);
    }
    
    @Test
    void testParseListCommand_WithValidStatus_ShouldReturnListByStatusCommand() {
        // ARRANGE
        String[] args = {"list", "todo"};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT
        assertNotNull(command);
        assertTrue(command instanceof ListByStatusCommand);
    }
    
    @Test
    void testParseListCommand_WithInProgressStatus_ShouldReturnListByStatusCommand() {
        // ARRANGE
        String[] args = {"list", "in-progress"};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT
        assertNotNull(command);
        assertTrue(command instanceof ListByStatusCommand);
    }
    
    @Test
    void testParseListCommand_WithDoneStatus_ShouldReturnListByStatusCommand() {
        // ARRANGE
        String[] args = {"list", "done"};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT
        assertNotNull(command);
        assertTrue(command instanceof ListByStatusCommand);
    }
    
    @Test
    void testParseListCommand_WithInvalidStatus_ShouldReturnNull() {
        // ARRANGE: Status không hợp lệ
        String[] args = {"list", "invalid-status"};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT
        assertNull(command);
    }
    
    // ===== TEST PARSE LANGUAGE COMMAND =====
    
    @Test
    void testParseLanguageCommand_WithVietnamese_ShouldReturnLanguageCommand() {
        // ARRANGE
        String[] args = {"language", "vi"};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT
        assertNotNull(command);
        assertTrue(command instanceof LanguageCommand);
    }
    
    @Test
    void testParseLanguageCommand_WithEnglish_ShouldReturnLanguageCommand() {
        // ARRANGE
        String[] args = {"language", "en"};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT
        assertNotNull(command);
        assertTrue(command instanceof LanguageCommand);
    }
    
    @Test
    void testParseLanguageCommand_WithInvalidLanguage_ShouldReturnNull() {
        // ARRANGE
        String[] args = {"language", "fr"};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT
        assertNull(command);
    }
    
    @Test
    void testParseLanguageCommand_WithMissingLanguage_ShouldReturnNull() {
        // ARRANGE
        String[] args = {"language"};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT
        assertNull(command);
    }
    
    // ===== TEST PARSE HELP COMMAND =====
    
    @Test
    void testParseHelpCommand_ShouldReturnHelpCommand() {
        // ARRANGE
        String[] args = {"help"};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT
        assertNotNull(command);
        assertTrue(command instanceof HelpCommand);
    }
    
    // ===== TEST UNKNOWN COMMAND =====
    
    @Test
    void testParseUnknownCommand_ShouldReturnNull() {
        // ARRANGE
        String[] args = {"unknown-command"};
        CommandParser parser = new CommandParser(args);
        
        // ACT
        Command command = parser.parse();
        
        // ASSERT
        assertNull(command);
    }
}
