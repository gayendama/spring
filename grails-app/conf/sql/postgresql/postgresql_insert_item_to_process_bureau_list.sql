INSERT INTO item_to_process (id, objet_id)
	SELECT
	  public.uuid_generate_v4(),
	  id
	FROM
	  bureau
	ORDER BY code_bureau
ON CONFLICT (objet_id) DO NOTHING;