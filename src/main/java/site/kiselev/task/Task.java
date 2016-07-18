package site.kiselev.task;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;


/**
 * Class for one task keeping
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class Task {

    public static final long ROOT_ID = 0;
    public static final int REMINDER_NOT_SET = 0;

    private long id;
    private List<Task> subTasks;
    private String subj = "";
    private State state = State.ACTIVE;
    private String description = "";
    private long reminder = REMINDER_NOT_SET;

    transient private Consumer<Task> saver;

    /**
     * Default constructor
     *
     * @param id        - Task ID
     * @param subTasks  - List of @{@link Task}
     */
    public Task(long id, List<Task> subTasks) {
        this.id = id;
        this.subTasks = subTasks;
    }

    /**
     * Constructor with new @{@link ArrayList} by default
     *
     * @param id        - Task ID
     */
    public Task(long id) {
        this(id, new ArrayList<>());
    }

    /**
     * Constructor with generate flag (any boolean)
     * ID will be generated.
     *
     * @param generate  - any @boolean, just a flag.
     */
    public Task(boolean generate) {
        this(generateID());
    }

    private Task() {
        this(0);
    }

    /**
     * Constructor
     *
     * @param subTasks  - List of Sub @{@link Task}
     */
    public Task(List<Task> subTasks) {
        this(generateID(), subTasks);
    }

    /**
     * Getter for ID
     * @return  - ID
     */
    public long getId() {
        return id;
    }

    /**
     * Getter for subj
     *
     * @return  - subj
     */
    public String getSubj() {
        if (id == ROOT_ID) throw new RuntimeException("Wrong call getSubj with id == ROOT_ID");
        return subj;
    }

    /**
     * Setter for subj
     *
     * @param subj  - subj
     * @return      - Self for conveyor
     */
    public Task setSubj(String subj) {
        if (id == ROOT_ID) throw new RuntimeException("Wrong call setSubj with id == ROOT_ID");
        this.subj = subj;
        return this;
    }

    public void move(Task from, Task to) {
        to.addSubTask(this);
        from.removeSubTask(this);

    }

    /**
     * Getter for Sub Tasks
     *
     * @return  - @{@link List} of @{@link Task}
     */
    public List<Task> getSubTasks() {
        return subTasks;
    }

    /**
     * Getter for Sub Tasks
     *
     * @return  - @{@link List} of @{@link Task}
     */
    public List<Task> getSubTasks(State state) {
        return subTasks.stream()
                .filter(t -> t.getState() == state)
                .collect(Collectors.toList());
    }

    /**
     * Adder for Sub Tasks
     *
     * @param task                      - @{@link Task} to add
     * @return                          - Self for conveyor
     * @throws IllegalStateException    - Throws if can't be added due to state
     */
    public Task addSubTask(Task task) throws IllegalStateException {
        if (!isCanBeAdded(task))
            throw new IllegalStateException(String.format("Can't add task %s to %s", task, this));
        this.subTasks.add(task);
        task.setSaver(this.saver);
        return this;
    }

    /**
     * Adder for Sub Tasks
     *
     * @param list                      - List of @{@link Task} to add
     * @return                          - lf for conveyor
     * @throws IllegalStateException    - Throws if can't be added due to state
     */
    public Task addSubTasks(List<Task> list) throws IllegalStateException {
        if (list.stream().anyMatch(t -> !isCanBeAdded(t)))
            throw new IllegalStateException(String.format("Can't add tasks %s to %s", list, this));
        this.subTasks.addAll(list);
        return this;
    }

    /**
     * Getter for description
     * @return  - description
     */
    public String getDescription() {
        if (id == ROOT_ID) throw new RuntimeException("Wrong call getDescription with id == ROOT_ID");
        return description;
    }

    /**
     * Setter for description
     *
     * @param description   - description
     * @return              - Self for conveyor
     */
    public Task setDescription(String description) {
        if (id == ROOT_ID) throw new RuntimeException("Wrong call setDescription with id == ROOT_ID");
        this.description = description;
        return this;
    }

    /**
     * Getter for state
     *
     * @return  - state
     */
    public State getState() {
        if (id == ROOT_ID) throw new RuntimeException("Wrong call getState with id == ROOT_ID");
        return state;
    }

    private Task setState(State state) throws IllegalStateException {
        if (id == ROOT_ID) throw new RuntimeException("Wrong call setState with id == ROOT_ID");
        checkState(state);
        setSubStates(state);
        this.state = state;
        return this;
    }

    /**
     * Set task from active to done state
     *
     * @return                          - Self for conveyor
     * @throws IllegalStateException    - Can't set task to done
     */
    public Task done () throws IllegalStateException {
        if (id == ROOT_ID) throw new RuntimeException("Wrong call done with id == ROOT_ID");
        return setState(State.DONE);
    }

    /**
     * Set task from done to active state
     *
     * @return                          - Self for conveyor
     * @throws IllegalStateException    - Can't set task to active
     */
    public Task undone() throws IllegalStateException {
        if (id == ROOT_ID) throw new RuntimeException("Wrong call undone with id == ROOT_ID");
        return setState(State.ACTIVE);
    }

    /**
     * Set task from done to archived state
     *
     * @return                          - Self for conveyor
     * @throws IllegalStateException    - Can't set task to archived
     */
    public Task archive() throws IllegalStateException {
        if (id == ROOT_ID) throw new RuntimeException("Wrong call archive with id == ROOT_ID");
        return setState(State.ARCHIVED);
    }

    /**
     * Set task from archived to done state
     *
     * @return                          - Self for conveyor
     * @throws IllegalStateException    - Can't set task to done
     */
    public Task unarchive() throws IllegalStateException {
        if (id == ROOT_ID) throw new RuntimeException("Wrong call unarchive with id == ROOT_ID");
        return setState(State.DONE);
    }

    /**
     * Getter for reminder
     *
     * @return  - reminder
     */
    public long getReminder() {
        return reminder;
    }

    /**
     * Setter for reminder
     *
     * @param reminder  - reminder
     * @return          - Self for conveyor
     */
    public Task setReminder(long reminder) {
        if (id == ROOT_ID) throw new RuntimeException("Wrong call setReminder with id == ROOT_ID");
        if (reminder == REMINDER_NOT_SET)
            throw new IllegalStateException("Reminder can't be equal REMINDER_NOT_SET");
        this.reminder = reminder;
        return this;
    }

    /**
     * Clears reminder
     *
     * @return                          - Self for conveyor
     * @throws IllegalStateException    - throws if there was no reminder
     */
    public Task clearReminder() throws IllegalStateException{
        if (id == ROOT_ID) throw new RuntimeException("Wrong call clearReminder with id == ROOT_ID");
        if (!isReminderSet())
            throw new IllegalStateException("Couldn't clear reminder: it wasn't set");
        this.reminder = REMINDER_NOT_SET;
        return this;
    }

    /**
     * Checks if it's time to remind
     *
     * @param timestamp - timestamp to check
     * @return          - @true if it's time
     */
    public boolean isItIsTime(double timestamp) {
        return isReminderSet() && reminder < timestamp;
    }

    /**
     * If reminder is set
     *
     * @return  - @true if reminder is set
     */
    public boolean isReminderSet() {
        return reminder != REMINDER_NOT_SET;
    }

    public Task findByID(long id) {
        if (this.id == id) return this;
        for (Task t : getSubTasks()) {
            Task found = t.findByID(id);
            if (found != null) return found;
        }
        return null;
    }

    public List<Task> findByName(String query) {
        List<Task> result = new ArrayList<>();
        if (this.subj.contains(query)) result.add(this);
        for (Task t : getSubTasks()) result.addAll(t.findByName(query));
        return result;
    }

    public static Task fromJSON(String json) throws JsonSyntaxException {
        // json can be null if we can't read such key
        if (json == null) return null;

        Gson gson = new Gson();
        Task result;
        try {
            result = gson.fromJson(json, Task.class);
        } catch (JsonSyntaxException | NullPointerException e) {
            throw new JsonSyntaxException("Error in JSON parsing", e);
        }
        return result;
    }

    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", subTasks=" + subTasks +
                ", subj='" + subj + '\'' +
                ", state=" + state +
                ", description='" + description + '\'' +
                ", reminder=" + reminder +
                '}';
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (id != task.id) return false;
        if (reminder != task.reminder) return false;
        if (!subTasks.equals(task.subTasks)) return false;
        if (subj != null ? !subj.equals(task.subj) : task.subj != null) return false;
        if (state != task.state) return false;
        return description != null ? description.equals(task.description) : task.description == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + subTasks.hashCode();
        result = 31 * result + (subj != null ? subj.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) (reminder ^ (reminder >>> 32));
        return result;
    }

    /**
     * Method to generate new ID
     *
     * @return  - @long of new unique ID
     */
    private static long generateID() {
        //TODO: Реализовать
        throw new NotImplementedException();
    }

    /**
     * Check is task can be added.
     *
     * State check:
     * this.ACTIVE => All states
     * this.DONE => except ACTIVE
     * this.ARCHIVED => ARCHIVED only
     *
     * @param other - other @{@link Task}
     * @return      - @boolean
     */
    private boolean isCanBeAdded(Task other) {
        if (this.id == ROOT_ID) return true;
        switch (this.getState()) {
            case ACTIVE:
                return true;
            case DONE:
                return other.getState() != State.ACTIVE;
            case ARCHIVED:
                return other.getState() == State.ARCHIVED;
        }
        return false;
    }

    private void removeSubTask(Task task) {
        subTasks.remove(task);
    }

    private void checkState(State state) throws IllegalStateException {
        // Same state is prohibited
        if (this.state == state)
            throw new IllegalStateException(
                    String.format("Couldn't set %s state for task (%d) %s. Current state is the same.",
                            state, this.getId(), this.getSubj()));

        // Jumping through the DONE state is prohibited
        if (    (this.state == State.ACTIVE && state == State.ARCHIVED) ||
                (this.state == State.ARCHIVED && state == State.ACTIVE))
            throw new IllegalStateException(
                    String.format("Couldn't set %s state for task (%d) %s with current state %s",
                            state, this.getId(), this.getSubj(), this.getState()));
    }

    private void setSubStates(State state) throws IllegalStateException {
        getSubTasks().stream()
                .filter(task -> task.getState() != state)
                .forEach(t -> {
                    if (t.getState() == State.ACTIVE && state == State.ARCHIVED) t.done();
                    if (t.getState() == State.ARCHIVED && state == State.ACTIVE) t.unarchive();
                    t.setState(state);
                });
    }

    public void setSaver(Consumer<Task> saver) {
        this.saver = saver;
        for (Task task : subTasks) {
            task.setSaver(saver);
        }
    }

    public void save() {
        if (saver == null) throw new NullPointerException("Saver was not set");
        saver.accept(this);
    }
}
