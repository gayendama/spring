insert into item_to_process (id, objet_id)
    select
		public.uuid_generate_v4(),
		id
	from credit_extourne
ON CONFLICT (objet_id) DO NOTHING