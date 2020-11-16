INSERT INTO public.task(
            id, task_container_id, title, state, solution, labels, technologies, 
            description, story_points, majority, likes, complexity, deadline, 
            type, components, version, creation_date)
    VALUES (1, 4, 'Task 1', 'xxx', '', '', '', 'Description of task', 1, 1, 0, 10, 
            now(), 'Task', '', 0, now());