DELETE FROM public.clients WHERE username = 'admin';

INSERT INTO public.clients (address, last_name, "name", "password", username)
VALUES ('test', 'test', 'test', '$2b$10$lGQcU3N2e1mrot17CTeLL.bulo9v/4yLlsJPAj2m7SmXLSFltISb.', 'admin')
;
