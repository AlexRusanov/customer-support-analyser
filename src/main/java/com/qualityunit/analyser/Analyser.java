package com.qualityunit.analyser;

import com.qualityunit.model.QueryLine;
import com.qualityunit.model.ResponseType;
import com.qualityunit.model.WaitingTimeline;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class Analyser {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static void analyse (BufferedReader input) throws IOException {
        StringBuilder output = new StringBuilder();
        List<WaitingTimeline> waitingTimelines = new ArrayList<>();
        int linesCount = Integer.parseInt(input.readLine());

        for (int i = 1; i <= linesCount; i++) {
            String currLine = input.readLine();

            if (currLine.charAt(0) == 'C') {
                waitingTimelines.add(parseWaitingTimeLine(currLine));
            }

            if (currLine.charAt(0) == 'D') {
                QueryLine currQueryLine = parseQueryLine(currLine);

                List<WaitingTimeline> list = filterWaitingTimelinesByDateAndResponseType(currQueryLine, waitingTimelines);

                if (currQueryLine.getServiceId().equals("*") && currQueryLine.getQuestionTypeId().equals("*")) {
                    OptionalDouble avg = getAverageWaitingTimeFromList(list);

                    addOutputLine(avg, output);
                } else if (currQueryLine.getServiceId().equals("*") && !currQueryLine.getQuestionTypeId().equals("*")) {
                    List<WaitingTimeline> listFilteredByServiceAndQuestion = filterWaitingTimelinesByQuestion(list, currQueryLine);

                    OptionalDouble avg = getAverageWaitingTimeFromList(listFilteredByServiceAndQuestion);

                    addOutputLine(avg, output);
                } else if (!currQueryLine.getServiceId().equals("*") && currQueryLine.getQuestionTypeId().equals("*")) {
                    List<WaitingTimeline> listFilteredByService = filterWaitingTimelinesByService(list, currQueryLine);

                    OptionalDouble avg = getAverageWaitingTimeFromList(listFilteredByService);

                    addOutputLine(avg, output);
                } else {
                    List<WaitingTimeline> listFilteredByService = filterWaitingTimelinesByService(list, currQueryLine);
                    List<WaitingTimeline> listFilteredByServiceAndQuestion = filterWaitingTimelinesByQuestion(listFilteredByService, currQueryLine);

                    OptionalDouble avg = getAverageWaitingTimeFromList(listFilteredByServiceAndQuestion);

                    addOutputLine(avg, output);
                }
            }
        }
        System.out.println(output);
    }

    private static WaitingTimeline parseWaitingTimeLine(String currLine) {
        String[] currWaitingTimeLine = currLine.split(" ");

        return new WaitingTimeline(
                currWaitingTimeLine,
                ResponseType.valueOf(currWaitingTimeLine[3]),
                LocalDate.parse(currWaitingTimeLine[4], formatter),
                Integer.parseInt(currWaitingTimeLine[5]));
    }

    private static QueryLine parseQueryLine (String currLine) {
        String[] queryLine = currLine.split(" ");
        String[] dates = queryLine[4].split("-");

        return new QueryLine(
                queryLine,
                ResponseType.valueOf(queryLine[3]),
                LocalDate.parse(dates[0], formatter),
                dates.length == 2 ? LocalDate.parse(dates[1], formatter) : LocalDate.now()
        );
    }

    private static List<WaitingTimeline> filterWaitingTimelinesByDateAndResponseType (QueryLine currQueryLine, List<WaitingTimeline> waitingTimelines) {
        return waitingTimelines.stream()
                .filter(elem -> ( elem.getDate().isAfter( currQueryLine.getStart() ) ) && ( elem.getDate().isBefore( currQueryLine.getEnd() ) ))
                .filter(elem -> elem.getResponseType() == currQueryLine.getResponseType())
                .collect(Collectors.toList());
    }

    private static StringBuilder addOutputLine (OptionalDouble optionalDouble, StringBuilder output) {
        if (optionalDouble.isPresent()) {
            output.append((int) optionalDouble.getAsDouble()).append("\n");
        } else {
            output.append("-").append("\n");
        }

        return output;
    }

    private static List<WaitingTimeline> filterWaitingTimelinesByService (List<WaitingTimeline> list, QueryLine currQueryLine) {
        if (currQueryLine.getServiceVariationId() != null) {
            return list.stream()
                    .filter(elem -> (elem.getServiceId().equals(currQueryLine.getServiceId()) &&
                                    elem.getServiceVariationId().equals(currQueryLine.getServiceVariationId())
                            )
                    )
                    .collect(Collectors.toList());
        } else {
            return list.stream()
                    .filter(elem -> elem.getServiceId().equals(currQueryLine.getServiceId()))
                    .collect(Collectors.toList());
        }
    }

    private static List<WaitingTimeline> filterWaitingTimelinesByQuestion (List<WaitingTimeline> list, QueryLine currQueryLine) {
        if(currQueryLine.getQuestionCategoryId() != null && currQueryLine.getQuestionSubCategoryId() != null) {
            return list.stream()
                    .filter(elem -> elem.getQuestionTypeId().equals(currQueryLine.getQuestionTypeId()) &&
                            elem.getQuestionCategoryId().equals(currQueryLine.getQuestionCategoryId()) &&
                            elem.getQuestionSubCategoryId().equals(currQueryLine.getQuestionSubCategoryId())
                    )
                    .collect(Collectors.toList());
        } else if (currQueryLine.getQuestionCategoryId() != null && currQueryLine.getQuestionSubCategoryId() == null){
            return list.stream()
                    .filter(elem -> elem.getQuestionTypeId().equals(currQueryLine.getQuestionTypeId()) &&
                            elem.getQuestionCategoryId().equals(currQueryLine.getQuestionCategoryId())
                    )
                    .collect(Collectors.toList());
        } else if (currQueryLine.getQuestionCategoryId() == null && currQueryLine.getQuestionSubCategoryId() != null) {
            return list.stream()
                    .filter(elem -> elem.getQuestionTypeId().equals(currQueryLine.getQuestionTypeId()) &&
                            elem.getQuestionSubCategoryId().equals(currQueryLine.getQuestionSubCategoryId())
                    )
                    .collect(Collectors.toList());
        } else {
            return list.stream()
                    .filter(elem -> elem.getQuestionTypeId().equals(currQueryLine.getQuestionTypeId()))
                    .collect(Collectors.toList());
        }
    }

    private static OptionalDouble getAverageWaitingTimeFromList (List<WaitingTimeline> waitingTimelineList) {
        return waitingTimelineList.stream()
                .mapToInt(WaitingTimeline::getWaitingTime)
                .average();
    }
}
