from typing import List


def validate_and_get_entity_id(entity_name, entity_prefix):
    entity_prefix = entity_prefix.lower()
    assert len(entity_prefix) == 1

    formed_id: str = entity_name.lower()
    parts: List[str] = formed_id.split("_")
    if len(parts) < 2:
        raise ValueError(entity_name)
    formed_id = parts[1]
    if parts[0] != entity_prefix:
        raise ValueError(entity_name)
    try:
        formed_id = int(formed_id)
    except ValueError:
        raise ValueError(entity_name)
    return formed_id


def get_entity_name_from_id(entity_id, entity_prefix):
    entity_prefix = entity_prefix.upper()
    assert len(entity_prefix) == 1

    return entity_prefix + "_" + str(entity_id)
