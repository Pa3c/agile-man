INSERT INTO public.task_container(
	id, overcontainer_id, type, title, closed, version, creation_date, created_by, team_in_project_id)
	VALUES (1, null,'SPRINT', 'Sprint 1', false, 0, now(),'pklos', 1);
INSERT INTO public.task_container(
	id, overcontainer_id, type, title, closed, version, creation_date, created_by, team_in_project_id)
	VALUES (2, null,'SPRINT', 'Sprint 2', true, 0, now(),'pklos', 1);
INSERT INTO public.task_container(
	id, overcontainer_id, type, title, closed, version, creation_date, created_by, team_in_project_id)
	VALUES (3, 2,'SPRINT', 'Sub Sprint 3', false, 0, now(),'pklos', 1);
INSERT INTO public.task_container(
	id, overcontainer_id, type, title, closed, version, creation_date, created_by, team_in_project_id)
	VALUES (4, null,'SPRINT', 'Sprint czworka', true, 0, now(),'user_x', 1);
	
	
INSERT INTO public.task_container(
	id, overcontainer_id, type, title, closed, version, creation_date, created_by, team_in_project_id)
	VALUES (5, null,'XP', 'XP container 1', false, 0, now(),'pklos', 2);
INSERT INTO public.task_container(
	id, overcontainer_id, type, title, closed, version, creation_date, created_by, team_in_project_id)
	VALUES (6, null,'XP', 'XP container 2', false, 0, now(),'pklos', 2);
INSERT INTO public.task_container(
	id, overcontainer_id, type, title, closed, version, creation_date, created_by, team_in_project_id)
	VALUES (7, 6,'XP', 'XP sub container of 2', false, 0, now(),'pklos', 2);
	
	

INSERT INTO public.task_container(
	id, overcontainer_id, type, title, closed, version, creation_date, created_by, team_in_project_id)
	VALUES (8, null,'SPRINT', 'Sprint from project 4', false, 0, now(),'pklos', 4);
INSERT INTO public.task_container(
	id, overcontainer_id, type, title, closed, version, creation_date, created_by, team_in_project_id)
	VALUES (9, null,'SPRINT', 'Sprint 2 from project 4', true, 0, now(),'user_x', 4);