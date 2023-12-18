
# Custom Calendar View

Custom Calendar View is a customizable calendar component for displaying and interacting with dates and events



## Features

- **Customizable Appearance:** Easily change the appearance of the calendar by adjusting colors, and other visual elements.
    
    Customize Calendar appearance by applying these custom attributes: 
    - **firstWeekDaySun:** Boolean to specify whether the first day of the week is Sunday (true) or Monday (false).By default false
    - **headerBackgroundColor:** Background color of the calendar header.By default holo_blue_dark ##0099CC
    - **headerTextColor:** Text color of the calendar header and previous and next month icons. By default white #FFFFFF
    - **calendarBackgroundColor:** Background color of the calendar grid.By default tertiary #DEBCDF
    - **selectedDayTextColor:** Text color of the selected day.By default red #FF4444
    - **currentDayBackgroundColor:** Background color of the current day. By default dark grey #AAAAAA
    - **previousMonthiconDrawable:** set darawale for "navigate to previous month button. By default "<-"
    - **nextMonthiconDrawable:** set darawale for "navigate to next month" button. By default "->"


- **Event Integration:** Highlight the days associated with events:

    To display events in the calendar call setEvents    method  from customCalendarLayout component and pass a list of days containing events in below format: 

        CustomCalendarDay(
        val day: Int,
        val month: Int,
        val year: Int,
        val events: List<Any>
        )

    customCalendarLayout.setEvents(list<CustomCalendarDay>)

     Once events are provided calendar will add red dot to the days that are associated with one or more events.

- **Easy implementation of custom calendar click events.**
    To implement calendar click events call setOnCalendarClickListener method from customCalendarLayout as in example bolow: 

        customCalendarLayout.setOnCalendarClickListener(object :
            CustomCalendarLayout.CalendarClickListenerInterface {
            override fun onDayClick(customCalendarDay: CustomCalendarDay) {}

            override fun onPreviousMonthClick(previousYear: Int, previousMonth: Int) {}

            override fun onNextMonthClick(nextYear: Int, nextMonth: Int) {}

            override fun onCurrentMonthClick(currentYear: Int, currentMonth: Int) {}
        })


    - The * *onDayClick* * listener provides selected day in  CustomCalendarDay format that holds events associatedwith that day. To use events in your application cast received events to your event Entity. 

    override fun onDayClick(customCalendarDay: CustomCalendarDay) {

    val events = customCalendarDay.events as List<MyEventEntity>}


    - The *onPreviousMonthClick* listener provides the year and month parameters as integers corresponding to the previous month relative to the one currently displayed on the screen.

    - The *onNextMonthClick*  listener provides the year and month parameters as integers corresponding to the next month relative to the one currently displayed on the screen.


    - The *onCurrentMonthClick* listener provides the year and month parameters as integers corresponding to the current date.


- **Month Navigation:** Quickly navigate between months using intuitive next and previous month buttons.

    - By pressing on previous or next month icons at the top of the calendar you can navigate between months. If Text indicating selected month and year is pressed, calendar will return to current year and month.






## Usage

To be added


## Developed by Paulius Mockevicius 
https://github.com/pmockevicius



