INSERT INTO public.task_container(
	overcontainer_id, type, title, closed, version, creation_date, created_by, team_in_project_id)
	VALUES ( null,'BACKLOG', 'Sprint 1', false, 0, now(),'pklos', 4);
INSERT INTO public.task_container(
	overcontainer_id, type, title, closed, version, creation_date, created_by, team_in_project_id)
	VALUES ( null,'COMMON', 'Sprint 2', true, 0, now(),'pklos', 4);
INSERT INTO public.task_container(
	overcontainer_id, type, title, closed, version, creation_date, created_by, team_in_project_id)
	VALUES ( 2,'COMMON', 'Sub Sprint 3', false, 0, now(),'pklos', 4);
INSERT INTO public.task_container(
	overcontainer_id, type, title, closed, version, creation_date, created_by, team_in_project_id)
	VALUES ( null,'BACKLOG', 'Sprint czworka', true, 0, now(),'user_x', 5);
	
	
INSERT INTO public.task_container(
	overcontainer_id, type, title, closed, version, creation_date, created_by, team_in_project_id)
	VALUES ( null,'BACKLOG', 'XP container 1', false, 0, now(),'pklos', 7);
INSERT INTO public.task_container(
	overcontainer_id, type, title, closed, version, creation_date, created_by, team_in_project_id)
	VALUES ( null,'BACKLOG', 'XP container 2', false, 0, now(),'pklos', 7);
INSERT INTO public.task_container(
	overcontainer_id, type, title, closed, version, creation_date, created_by, team_in_project_id)
	VALUES ( 6,'COMMON', 'XP sub container of 2', false, 0, now(),'pklos',7);
	
	

INSERT INTO public.task_container(
	overcontainer_id, type, title, closed, version, creation_date, created_by, team_in_project_id)
	VALUES (null,'BACKLOG', 'Sprint from project 4', false, 0, now(),'pklos', 6);
INSERT INTO public.task_container(
	overcontainer_id, type, title, closed, version, creation_date, created_by, team_in_project_id)
	VALUES (null,'COMMON', 'Sprint 2 from project 4', true, 0, now(),'user_x', 6);