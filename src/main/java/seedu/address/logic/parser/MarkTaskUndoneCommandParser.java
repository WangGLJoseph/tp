package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_ID;

import java.util.stream.Stream;

import seedu.address.logic.commands.MarkTaskUndoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleName;
import seedu.address.model.module.student.StudentId;
import seedu.address.model.task.TaskId;

/**
 * Parses input arguments and creates a new MarkTaskUndoneCommand object.
 */
public class MarkTaskUndoneCommandParser implements Parser<MarkTaskUndoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkTaskUndoneCommand
     * and returns a MarkTaskUndoneCommand object for execution.
     *
     * @param args Args for marking a task as undone.
     * @return MarkTaskUndoneCommand object.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public MarkTaskUndoneCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE_NAME, PREFIX_STUDENT_ID, PREFIX_TASK_ID);
        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_NAME, PREFIX_STUDENT_ID, PREFIX_TASK_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkTaskUndoneCommand.MESSAGE_USAGE));
        }

        StudentId studentId = ParserUtil.parseStudentId(argMultimap.getValue(PREFIX_STUDENT_ID).get());
        ModuleName moduleName = ParserUtil.parseModuleName(argMultimap.getValue(PREFIX_MODULE_NAME).get());
        TaskId taskId = ParserUtil.parseTaskId(argMultimap.getValue(PREFIX_TASK_ID).get());

        return new MarkTaskUndoneCommand(moduleName, studentId, taskId);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
